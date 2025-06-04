package ru.bfe.efep.app.inspection.tr.row

import org.springframework.data.jpa.repository.JpaRepository

interface TechnicalReportRowRepository : JpaRepository<TechnicalReportRow, Long> {
    fun deleteByTechnicalReportId(technicalReportId: Long)
}