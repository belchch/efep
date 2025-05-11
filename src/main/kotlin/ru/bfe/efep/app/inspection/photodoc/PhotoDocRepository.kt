package ru.bfe.efep.app.inspection.photodoc

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface PhotoDocRepository : JpaRepository<PhotoDoc, Long> {
    fun findByIdAndInspectionId(id: Long, inspectionId: Long): PhotoDoc?

    @Query("SELECT p FROM PhotoDoc p WHERE p.inspection.id = :inspectionId " +
            "AND (:spotId IS NULL OR p.spot.id = :spotId) " +
            "AND (:materialId IS NULL OR p.material.id = :materialId) " +
            "AND (:structElemId IS NULL OR p.structElem.id = :structElemId) " +
            "AND (:type IS NULL OR p.type = :type)")
    fun search(
        @Param("inspectionId") inspectionId: Long,
        @Param("spotId") spotId: Long?,
        @Param("materialId") materialId: Long?,
        @Param("structElemId") structElemId: Long?,
        @Param("type") type: PhotoDocType?
    ): List<PhotoDoc>
}