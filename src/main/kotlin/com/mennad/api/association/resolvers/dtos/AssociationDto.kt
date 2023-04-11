package com.mennad.api.association.resolvers.dtos

import org.springframework.data.mongodb.core.mapping.Document

@Document
class AssociationDto (
    val id: String?,
    val name: String?,
    val activity: String?,
    val funds: Int?,
    val founder: String?
)