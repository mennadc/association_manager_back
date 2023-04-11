package com.mennad.api.association.domain.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.util.*

@Document(collection = "events")
data class Event (
    var title: String,
    var description: String,
    var budget: Int,
    var associationId: String,
    var organizer: String,
) {
    @Id
    var id: String = ""
}

