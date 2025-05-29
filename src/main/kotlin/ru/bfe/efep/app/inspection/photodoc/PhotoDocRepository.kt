package ru.bfe.efep.app.inspection.photodoc

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface PhotoDocRepository : JpaRepository<PhotoDoc, Long>, JpaSpecificationExecutor<PhotoDoc> {
    fun findByIdAndInspectionId(id: Long, inspectionId: Long): PhotoDoc?
    fun findByInspectionId(inspectionId: Long): List<PhotoDoc>
}
