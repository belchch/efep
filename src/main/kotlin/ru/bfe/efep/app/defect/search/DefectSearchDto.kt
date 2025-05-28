package ru.bfe.efep.app.defect.search

import ru.bfe.efep.app.defect.DefectResponse
import ru.bfe.efep.app.defect.flaw.FlawResponse
import ru.bfe.efep.app.material.MaterialResponse
import ru.bfe.efep.app.structelem.StructElemResponse

class DefectSearchRequest(
    val structElemId: Long?,
    val materialId: Long?,
    val flawId: Long?,
    val defectId: Long?,
)

class DefectSearchResponse(
    val structElems: List<StructElemResponse>,
    val materials: List<MaterialResponse>,
    val flaws: List<FlawResponse>,
    val defects: List<DefectResponse>
)