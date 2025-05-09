package ru.bfe.efep.app.company

data class CompanyCreateRequest(
    val name: String,
    val inn: String? = null
)

data class CompanyUpdateRequest(
    val name: String? = null,
    val inn: String? = null
)

data class CompanyResponse(
    val id: Long,
    val name: String,
    val inn: String?
)

fun CompanyCreateRequest.toEntity() = Company(
    name = name,
    inn = inn
)

fun CompanyUpdateRequest.toEntity(id: Long) = Company(
    id = id,
    name = name ?: throw IllegalArgumentException("Name cannot be null for update"),
    inn = inn
)

fun Company.toResponse() = CompanyResponse(
    id = id!!,
    name = name,
    inn = inn
)