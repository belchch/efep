package ru.bfe.efep.app.judge

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import ru.bfe.efep.app.court.CourtRepository

@Service
class JudgeService(
    private val judgeRepository: JudgeRepository,
    private val courtRepository: CourtRepository
) {

    fun createJudge(request: JudgeCreateRequest): JudgeResponse {
        return judgeRepository.save(request.toEntity(courtRepository)).toResponse()
    }

    fun getJudge(id: Long): JudgeResponse {
        return judgeRepository.findById(id).map { it.toResponse() }
            .orElseThrow { notFoundException(id) }
    }

    fun getAllJudges(): List<JudgeResponse> {
        return judgeRepository.findAll().map { it.toResponse() }
    }

    fun updateJudge(
        id: Long,
        request: JudgeUpdateRequest
    ): JudgeResponse {
        if (!judgeRepository.existsById(id)) {
            notFoundException(id)
        }

        return judgeRepository.save(request.toEntity(id, courtRepository)).toResponse()
    }

    fun deleteJudge(id: Long) {
        judgeRepository.deleteById(id)
    }
}

private fun notFoundException(id: Long) = EntityNotFoundException("Judge not found with id: $id")