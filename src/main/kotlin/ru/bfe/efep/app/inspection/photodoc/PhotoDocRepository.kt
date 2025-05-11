package ru.bfe.efep.app.inspection.photodoc

import org.springframework.data.jpa.repository.JpaRepository

interface PhotoDocRepository : JpaRepository<PhotoDoc, Long> {
    fun findByInspectionId(inspectionId: Long): List<PhotoDoc>
    fun findByIdAndInspectionId(id: Long, inspectionId: Long): PhotoDoc?
}