package ru.bfe.efep.app.user

data class UserCreateRequest(
    val username: String,
    val password: String,
)

data class UserUpdateRequest(
    val username: String,
    val password: String? = null,
)

data class UserResponse(
    val id: Long,
    val username: String,
)

fun UserCreateRequest.toEntity() = User(
    username = username,
    password = password,
)

fun UserUpdateRequest.toEntity(existing: User): User {
    return User(
        id = existing.id,
        username = username,
        password = password ?: existing.password
    )
}

fun User.toResponse() = UserResponse(
    id = id!!,
    username = username
)