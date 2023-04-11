package com.mennad.api.association.domain.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "associations")
data class Association(
    var name: String,
    var activity: String,
    var funds: Int,
    var founder: String
) {
    @Id
    var id: String = ""
}