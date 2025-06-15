package ru.bfe.efep.app.gv.report

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import ru.bfe.efep.app.inspection.InspectionRepository
import ru.bfe.efep.app.inspection.photodoc.PhotoDocRepository
import ru.bfe.efep.app.inspection.photodoc.PhotoDocType
import ru.bfe.efep.app.s3.S3Service

@Service
class GeneralViewReportService(
    private val inspectionRepository: InspectionRepository,
    private val photoDocRepository: PhotoDocRepository,
    private val generalViewReportRepository: GeneralViewReportRepository,
    private val generalViewReportRowRepository: GeneralViewReportRowRepository,
    private val generalViewReportItemRepository: GeneralViewReportItemRepository,
    private val generalViewReportPhotoRepository: GeneralViewReportPhotoRepository,
    private val s3Service: S3Service
) {

    @Transactional
    fun updateReport(reportRequest: GeneralViewReportUpdateRequest): GeneralViewReportResponse {
        val report = generalViewReportRepository.findByInspectionId(inspectionId = reportRequest.inspectionId)!!

        generalViewReportRowRepository.deleteByReportIdAndIdNotIn(reportRequest.id, reportRequest.rows.mapNotNull { it.id })
        for (rowRequest in reportRequest.rows) {
            val row = generalViewReportRowRepository.save(rowRequest.toEntity(report))

            generalViewReportItemRepository.deleteByRowIdAndIdNotIn(row.id!!, rowRequest.items.mapNotNull { it.id })
            for (itemRequest in rowRequest.items) {
                val item = generalViewReportItemRepository.save(itemRequest.toEntity(row))

                generalViewReportPhotoRepository.deleteByItemIdAndIdNotIn(item.id!!, itemRequest.photos.mapNotNull { it.id })
                for (photoRequest in itemRequest.photos) {
                    generalViewReportPhotoRepository.save(photoRequest.toEntity(item))
                }
            }
        }

        generalViewReportRepository.flush()
        return generalViewReportRepository.findById(report.id!!).get().toResponse()
    }

    fun getReport(inspectionId: Long): GeneralViewReportResponse? {
        return generalViewReportRepository.findByInspectionId(inspectionId)?.also { generateUrls(it) }?.toResponse()
    }

    fun getGallery(inspectionId: Long): List<GeneralViewReportGalleryGroup>? {
        val usedPhotoSources = generalViewReportRepository.findByInspectionId(inspectionId)?.rows
            ?.flatMap { row -> row.items.flatMap { item -> item.photos.map { it.source } } }

        return if (usedPhotoSources != null) {
            photoDocRepository.findByInspectionId(inspectionId)
                .groupBy { it.spot }
                .mapValues { entry ->
                    entry.value.flatMap {
                            phd -> phd.sources.mapIndexed { index, source -> Pair(source, phd.urls[index]) }
                    }.filter { (source) -> !usedPhotoSources.any { it == source } }
                        .map { (source, url) -> GeneralViewReportGalleryPhoto(
                            source = source,
                            url = url
                        ) }
                }.map { GeneralViewReportGalleryGroup(
                    name = it.key?.name,
                    photos = it.value
                ) }.filter {
                    it.photos.isNotEmpty()
                }
        } else {
            null
        }
    }

    @Transactional
    fun buildReport(inspectionId: Long) {
        deleteReport(inspectionId)
        val inspection = inspectionRepository.findById(inspectionId).get()

        val report = generalViewReportRepository.save(GeneralViewReport(
            inspection = inspection
        ))

        var photoIndex = 1

        photoDocRepository.findByInspectionId(inspectionId)
            .filter { it.type == PhotoDocType.GENERAL_VIEW && it.spot != null}
            .groupBy { it.spot }
            .mapValues { entry -> entry.value.flatMap { it.sources }.take(2) }
            .map { entry ->
                val row = generalViewReportRowRepository.save(GeneralViewReportRow(
                    report = report
                ))

                val item = generalViewReportItemRepository.save(GeneralViewReportItem(
                    row = row,
                    text = "Фото ${photoIndex++}. ${entry.key?.name}"
                ))

                for (source in entry.value) {
                    generalViewReportPhotoRepository.save(GeneralViewReportPhoto(
                        source = source,
                        item = item
                    ))
                }
            }
    }

    private fun deleteReport(inspectionId: Long) {
        generalViewReportRepository.findByInspectionId(inspectionId)?.let { report ->
            val rowIds = report.rows.map { it.id }
            generalViewReportRowRepository.findAllById(rowIds).forEach { row ->
                val itemIds = row.items.map { it.id }
                generalViewReportItemRepository.findAllById(itemIds).forEach { item ->
                    generalViewReportPhotoRepository.deleteAllById(item.photos.map { it.id })
                }
                generalViewReportItemRepository.deleteAllById(itemIds)
            }
            generalViewReportRowRepository.deleteAllById(rowIds)
            generalViewReportRepository.deleteById(report.id!!)
        }
    }

    private fun generateUrls(report: GeneralViewReport) {
        report.rows.forEach { row ->
            row.items.forEach { item ->
                item.photos.forEach { photo ->
                    photo.url = s3Service.generateDownloadUrl(photo.source, 60*60)
                }
            }
        }
    }
}