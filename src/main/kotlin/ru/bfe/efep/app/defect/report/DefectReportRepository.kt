package ru.bfe.efep.app.defect.report

import org.springframework.data.jpa.repository.JpaRepository

interface DefectReportRepository : JpaRepository<DefectReport, Long> {
    fun deleteByInspectionId(inspectionId: Long)
    fun findAllByInspectionId(inspectionId: Long): List<DefectReport>
}