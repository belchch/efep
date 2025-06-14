package ru.bfe.efep.app.gv.report

import org.springframework.data.jpa.repository.JpaRepository

interface GeneralViewReportPhotoRepository : JpaRepository<GeneralViewReportPhoto, Long>
interface GeneralViewReportItemRepository : JpaRepository<GeneralViewReportItem, Long>
interface GeneralViewReportRowRepository : JpaRepository<GeneralViewReportRow, Long>
interface GeneralViewReportRepository : JpaRepository<GeneralViewReport, Long> {
    fun deleteByInspectionId(inspectionId: Long)
    fun findByInspectionId(inspectionId: Long): GeneralViewReport?
}