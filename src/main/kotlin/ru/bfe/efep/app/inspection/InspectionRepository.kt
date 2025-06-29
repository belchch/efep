package ru.bfe.efep.app.inspection

import org.springframework.data.jpa.repository.JpaRepository

interface InspectionRepository : JpaRepository<Inspection, Long> {
    fun findAllByCaseId(caseId: Long): List<Inspection>
}