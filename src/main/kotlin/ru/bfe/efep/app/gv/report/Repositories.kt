package ru.bfe.efep.app.gv.report

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional


interface GeneralViewReportPhotoRepository : JpaRepository<GeneralViewReportPhoto, Long> {
    @Transactional
    @Modifying
    @Query("""
        DELETE FROM GeneralViewReportPhoto p 
        WHERE p.item.id = :itemId 
        AND p.id NOT IN :ids
    """)
    fun deleteByItemIdAndIdNotIn(
        @Param("itemId") itemId: Long,
        @Param("ids") ids: List<Long>
    )
}

interface GeneralViewReportItemRepository : JpaRepository<GeneralViewReportItem, Long> {
    @Transactional
    @Modifying
    @Query("""
        DELETE FROM GeneralViewReportItem i 
        WHERE i.row.id = :rowId 
        AND i.id NOT IN :ids
    """)
    fun deleteByRowIdAndIdNotIn(
        @Param("rowId") rowId: Long,
        @Param("ids") ids: List<Long>
    )
}

interface GeneralViewReportRowRepository : JpaRepository<GeneralViewReportRow, Long> {
    @Transactional
    @Modifying
    @Query("""
        DELETE FROM GeneralViewReportRow r 
        WHERE r.report.id = :reportId 
        AND r.id NOT IN :ids
    """)
    fun deleteByReportIdAndIdNotIn(
        @Param("reportId") reportId: Long,
        @Param("ids") ids: List<Long>
    )
}

interface GeneralViewReportRepository : JpaRepository<GeneralViewReport, Long> {
    @Query("""
        SELECT r FROM GeneralViewReport r 
        WHERE r.inspection.id = :inspectionId
    """)
    fun findByInspectionId(@Param("inspectionId") inspectionId: Long): GeneralViewReport?
}