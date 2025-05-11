package ru.bfe.efep.app.user

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun createUser(request: UserCreateRequest): UserResponse {
        return userRepository.save(request.toEntity()).toResponse()
    }

    fun getUser(id: Long): UserResponse {
        return userRepository.findById(id).map { it.toResponse() }
            .orElseThrow { notFoundException(id) }
    }

    fun getAllUsers(): List<UserResponse> {
        return userRepository.findAll().map { it.toResponse() }
    }

    fun updateUser(
        id: Long,
        request: UserUpdateRequest
    ): UserResponse {
        val user = userRepository.findById(id).orElseThrow {
            notFoundException(id)
        }

        return userRepository.save(request.toEntity(user)).toResponse()
    }

    fun deleteUser(id: Long) {
        userRepository.deleteById(id)
    }
}

private fun notFoundException(id: Long) = EntityNotFoundException("User not found with id: $id")