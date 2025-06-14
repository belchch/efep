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

    fun updateReport(reportRequest: GeneralViewReportUpdateRequest): GeneralViewReportResponse {
        val inspection = inspectionRepository.findById(reportRequest.inspectionId).get()
        val report = generalViewReportRepository.save(reportRequest.toEntity(inspection))

        for (rowRequest in reportRequest.rows) {
            val row = generalViewReportRowRepository.save(rowRequest.toEntity(report))

            for (itemRequest in rowRequest.items) {
                val item = generalViewReportItemRepository.save(itemRequest.toEntity(row))

                for (photoRequest in itemRequest.photos) {
                    generalViewReportPhotoRepository.save(photoRequest.toEntity(item))
                }
            }
        }

        return generalViewReportRepository.findById(report.id!!).get().toResponse()
    }

    fun getReport(inspectionId: Long): GeneralViewReportResponse? {
        return generalViewReportRepository.findByInspectionId(inspectionId)?.also { generateUrls(it) }?.toResponse()
    }

    @Transactional
    fun buildReport(inspectionId: Long) {
        generalViewReportRepository.deleteByInspectionId(inspectionId)
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