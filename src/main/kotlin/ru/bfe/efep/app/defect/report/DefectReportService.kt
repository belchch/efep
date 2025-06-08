package ru.bfe.efep.app.defect.report

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import ru.bfe.efep.app.defect.report.photo.DefectReportPhoto
import ru.bfe.efep.app.defect.report.photo.DefectReportPhotoRepository
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
import ru.bfe.efep.app.standard.fullName
import kotlin.jvm.optionals.getOrNull

@Service
class DefectReportService(
    private val defectReportSpotRepository: DefectReportSpotRepository,
    private val defectReportStructElemRepository: DefectReportStructElemRepository,
    private val defectReportRowRepository: DefectReportRowRepository,
    private val defectReportPhotoRepository: DefectReportPhotoRepository,
    private val photoDocRepository: PhotoDocRepository,
    private val inspectionRepository: InspectionRepository,
    private val defectReportRepository: DefectReportRepository,
) {

    @Transactional
    fun buildReport(inspectionId: Long) {
        defectReportRepository.deleteByInspectionId(inspectionId)

        val inspection = inspectionRepository.findById(inspectionId).get()

        val report = defectReportRepository.save(DefectReport(
            inspection = inspection
        ))

        val defects = photoDocRepository.findByInspectionId(inspectionId).filter {
            it.type == PhotoDocType.DEFECT &&
                    it.defectInfo != null &&
                    it.spot != null &&
                    (it.defectInfo?.defect != null || it.defectInfo?.technicalReportRow != null)
        }

        defects.groupBy { it.spot!! }.forEach { spotEntry ->
            val spot = defectReportSpotRepository.save(DefectReportSpot(
                text = spotEntry.key.name,
                report = report,
            ))

            spotEntry.value.groupBy { it.defectInfo!!.structElem }.forEach { structElemEntry ->
                val structElem = defectReportStructElemRepository.save(DefectReportStructElem(
                    text = structElemEntry.key!!.name,
                    spot = spot
                ))

                structElemEntry.value.forEach { photoDoc ->
                    val row = defectReportRowRepository.save(
                        DefectReportRow(
                            technicalReport = photoDoc.defectInfo!!.technicalReportRow?.description,
                            defect = photoDoc.defectInfo!!.applyTemplate(),
                            standard = photoDoc.defectInfo!!.standard()?.fullName(),
                            structElem = structElem,
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

    @Transactional
    fun getReport(inspectionId: Long): DefectReportResponse? {
        return defectReportRepository.findAllByInspectionId(inspectionId).map {
            it.toResponse()
        }.firstOrNull()
    }
}