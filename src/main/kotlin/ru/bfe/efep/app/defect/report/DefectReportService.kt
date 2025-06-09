package ru.bfe.efep.app.defect.report

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import ru.bfe.efep.app.defect.report.photo.DefectReportPhoto
import ru.bfe.efep.app.defect.report.photo.DefectReportPhotoRepository
import ru.bfe.efep.app.defect.report.photo.DefectReportPhotoResponse
import ru.bfe.efep.app.defect.report.photo.toResponse
import ru.bfe.efep.app.defect.report.row.DefectReportRow
import ru.bfe.efep.app.defect.report.row.DefectReportRowRepository
import ru.bfe.efep.app.defect.report.spot.DefectReportSpot
import ru.bfe.efep.app.defect.report.spot.DefectReportSpotRepository
import ru.bfe.efep.app.defect.report.structelem.DefectReportStructElem
import ru.bfe.efep.app.defect.report.structelem.DefectReportStructElemRepository
import ru.bfe.efep.app.inspection.InspectionRepository
import ru.bfe.efep.app.inspection.photodoc.PhotoDocRepository
import ru.bfe.efep.app.inspection.photodoc.PhotoDocType
import ru.bfe.efep.app.inspection.photodoc.applyTemplate
import ru.bfe.efep.app.inspection.photodoc.standard
import ru.bfe.efep.app.s3.S3Service
import ru.bfe.efep.app.standard.fullName
import ru.bfe.efep.app.structelem.StructElemRepository

@Service
class DefectReportService(
    private val defectReportSpotRepository: DefectReportSpotRepository,
    private val defectReportStructElemRepository: DefectReportStructElemRepository,
    private val defectReportRowRepository: DefectReportRowRepository,
    private val defectReportPhotoRepository: DefectReportPhotoRepository,
    private val photoDocRepository: PhotoDocRepository,
    private val inspectionRepository: InspectionRepository,
    private val defectReportRepository: DefectReportRepository,
    private val s3Service: S3Service
) {

    @Transactional
    fun buildReport(inspectionId: Long, useTechnicalReport: Boolean) {
        defectReportRepository.deleteByInspectionId(inspectionId)

        val inspection = inspectionRepository.findById(inspectionId).get()

        val report = defectReportRepository.save(
            DefectReport(
                inspection = inspection,
                useTechnicalReport = useTechnicalReport,
            )
        )

        val defects = photoDocRepository.findByInspectionId(inspectionId).filter {
            it.type == PhotoDocType.DEFECT &&
                    it.defectInfo != null &&
                    it.spot != null &&
                    it.defectInfo?.structElem != null &&
                    (it.defectInfo?.defect != null || (it.defectInfo?.technicalReportRow != null && useTechnicalReport))
        }.sortedBy { it.id }

        defects.groupBy { it.spot!! }.toList().forEachIndexed { spotIndex, (spotKey, spotValue) ->
            val spot = defectReportSpotRepository.save(
                DefectReportSpot(
                    text = spotKey.name,
                    report = report,
                    sortOrder = spotIndex
                )
            )

            spotValue.groupBy { it.defectInfo!!.structElem }.toList()
                .forEachIndexed { structElemIndex, (structElemKey, structElemValue) ->
                    val structElem = defectReportStructElemRepository.save(
                        DefectReportStructElem(
                            text = structElemKey!!.name,
                            spot = spot,
                            sortOrder = structElemIndex
                        )
                    )

                    structElemValue.forEachIndexed { photoDocIndex, photoDoc ->
                        val row = defectReportRowRepository.save(
                            DefectReportRow(
                                technicalReport = photoDoc.defectInfo!!.technicalReportRow?.description,
                                defect = photoDoc.defectInfo!!.applyTemplate() ?: "Не выявлено",
                                standard = photoDoc.defectInfo!!.standard()?.fullName(),
                                structElem = structElem,
                                sortOrder = photoDocIndex
                            )
                        )

                        photoDoc.sources.withIndex().forEach { (index, source) ->
                            defectReportPhotoRepository.save(
                                DefectReportPhoto(
                                    source = source,
                                    row = row,
                                    used = index in listOf(0, 1)
                                )
                            )
                        }
                    }


                }
        }
    }

    fun getReport(inspectionId: Long): DefectReportResponse? {
        val reportEntity = defectReportRepository.findAllByInspectionId(inspectionId)

        val photos = reportEntity.flatMap { re -> re.spots.flatMap { spot -> spot.structElems.flatMap { se -> se.rows.flatMap { row -> row.photos } } } }

        photos.forEach {
            it.url = s3Service.generateDownloadUrl(it.source)
        }

        defectReportPhotoRepository.saveAll(photos)

        val report = reportEntity.map {
            it.toResponse()
        }.firstOrNull()

        report?.spots?.sortBy { it.sortOrder }

        report?.spots?.forEach { spot ->
            spot.structElems.sortBy { it.sortOrder }

            spot.structElems.forEach { structElem ->
                structElem.rows.sortBy { it.sortOrder }

                structElem.rows.forEach { row ->
                    row.photos.sortBy { it.id }
                }
            }
        }

        return report
    }

    @Transactional
    fun usePhoto(photoId: Long, use: Boolean, scope: Int): List<DefectReportPhotoResponse> {
        val photo = defectReportPhotoRepository.findById(photoId).get()
        val usedPhotos = photo.row.photos.filter { it.used }

        if (use && usedPhotos.size >= scope) {
            val firstUsed = usedPhotos.first()
            firstUsed.used = false
            defectReportPhotoRepository.save(firstUsed)
        }

        photo.used = use
        defectReportPhotoRepository.save(photo)

        return photo.row.photos.map { it.toResponse() }.sortedBy { it.id }
    }

    @Transactional
    fun moveSpot(spotId: Long, fromIndex: Int, toIndex: Int) {
        val spot = defectReportSpotRepository.findById(spotId).get()
        val spots = spot.report.spots
        spots.sortBy { it.sortOrder }
        spots.move(fromIndex, toIndex)

        spots.forEachIndexed { i, it ->
            it.sortOrder = i
        }

        defectReportSpotRepository.saveAll(spots)
    }

    @Transactional
    fun moveStructElem(structElemId: Long, fromIndex: Int, toIndex: Int) {
        val structElem = defectReportStructElemRepository.findById(structElemId).get()
        val structElems = structElem.spot.structElems
        structElems.sortBy { it.sortOrder }
        structElems.move(fromIndex, toIndex)

        structElems.forEachIndexed { i, it ->
            it.sortOrder = i
        }

        defectReportStructElemRepository.saveAll(structElems)
    }

    @Transactional
    fun moveRow(rowId: Long, fromIndex: Int, toIndex: Int) {
        val row = defectReportRowRepository.findById(rowId).get()
        val rows = row.structElem.rows
        rows.sortBy { it.sortOrder }
        rows.move(fromIndex, toIndex)

        rows.forEachIndexed { i, it ->
            it.sortOrder = i
        }

        defectReportRowRepository.saveAll(rows)
    }

}

fun <T> MutableList<T>.move(fromIndex: Int, toIndex: Int) {
    if (fromIndex == toIndex) return
    val item = this.removeAt(fromIndex)
    this.add(toIndex, item)
}