package ru.bfe.efep.app.defect.search

import org.springframework.stereotype.Service
import ru.bfe.efep.app.defect.DefectRepository
import ru.bfe.efep.app.defect.flaw.toResponse
import ru.bfe.efep.app.defect.toResponse
import ru.bfe.efep.app.material.toResponse
import ru.bfe.efep.app.structelem.toResponse

@Service
class DefectSearchService(
    private val defectRepository: DefectRepository
) {
    fun search(request: DefectSearchRequest): DefectSearchResponse {
        val defects = defectRepository.findAll(searchSpec(
            structElemId = request.structElemId,
            materialId = request.materialId,
            flawId = request.flawId,
            defectId = request.defectId
        ))

        return DefectSearchResponse(
            structElems = defects.map { it.structElem }.distinct().map { it.toResponse() },
            materials = defects.mapNotNull { it.material }.distinct().map { it.toResponse() },
            flaws = defects.mapNotNull { it.flaw }.distinct().map { it.toResponse() },
            defects = defects.map { it.toResponse() }
        )
    }
}