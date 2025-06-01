package ru.bfe.efep.app.inspection.tr

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.http.ResponseEntity

interface TechnicalReportRepository : JpaRepository<TechnicalReport, Long> {
    fun findByInspectionId(inspectionId: Long): TechnicalReport?
    fun deleteByInspectionId(inspectionId: Long)
}