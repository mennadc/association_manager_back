package com.mennad.api.association.resolvers.dtos

import org.springframework.data.mongodb.core.mapping.Document

@Document
class EventDto (
    val id: String?,
    val title: String?,
    val description: String?,
    val budget: Int?,
    val associationId: String?,
    val organizer: String?
)