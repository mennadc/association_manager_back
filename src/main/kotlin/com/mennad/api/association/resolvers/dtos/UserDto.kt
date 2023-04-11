package com.mennad.api.association.resolvers.dtos

import org.springframework.data.mongodb.core.mapping.Document

@Document
data class UserDto(
    val id: String?,
    val firstName: String?,
    val lastName: String?,
    val userName: String?,
    val email: String?,
    val password: String?,
    val wallet: Int?,
    val associationId: String?
)