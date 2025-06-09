package ru.bfe.efep.app.cases

import ru.bfe.efep.app.company.CompanyResponse
import ru.bfe.efep.app.court.CourtResponse
import ru.bfe.efep.app.judge.JudgeResponse
import ru.bfe.efep.app.region.RegionResponse
import ru.bfe.efep.app.user.UserResponse
import java.time.Instant

data class CaseCreateRequest(
    val number: String,
    val status: CaseStatus?,
    val priority: CasePriority?,
    val facilityAddress: String,
    val courtId: Long?,
    val judgeId: Long?,
    val companyId: Long,
    val regionId: Long,
    val deadline: Instant
)

data class CaseUpdateRequest(
    val number: String,
    val status: CaseStatus,
    val priority: CasePriority,
    val facilityAddress: String,
    val courtId: Long?,
    val judgeId: Long?,
    val companyId: Long,
    val regionId: Long,
    val deadline: Instant,
    val inspectionIds: List<Long>
)

data class CasePatchRequest(
    val number: String? = null,
    val status: CaseStatus? = null,
    val priority: CasePriority? = null,
    val facilityAddress: String? = null,
    val courtId: Long? = null,
    val judgeId: Long? = null,
    val companyId: Long? = null,
    val regionId: Long? = null,
    val deadline: Instant? = null,
    val inspectionIds: List<Long>? = null,
)

data class CaseResponse(
    val id: Long,
    val number: String,
    val status: CaseStatus,
    val priority: CasePriority,
    val facilityAddress: String,
    val court: CourtResponse?,
    val judge: JudgeResponse?,
    val company: CompanyResponse,
    val region: RegionResponse,
    val createdBy: UserResponse,
    val createdAt: Instant,
    val deadline: Instant,
    val inspectionIds: List<Long>,
    val stages: CaseStagesResponse
)

data class CaseStagesResponse(
    val inspection: Boolean,
    val generalView: Boolean,
    val defect: Boolean,
    val workVolume: Boolean
)