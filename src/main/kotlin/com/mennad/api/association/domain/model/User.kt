package com.mennad.api.association.domain.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "users")
data class User(
    var firstName: String,
    var lastName: String,
    var userName: String,
    var email: String,
    var password: String,
    var wallet: Int,
    var associationId: String
) {
    @Id
    var id: String = ""
}


