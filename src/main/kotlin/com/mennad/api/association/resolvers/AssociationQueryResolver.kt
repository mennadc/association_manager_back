package com.mennad.api.association.resolvers

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.mennad.api.association.domain.AssociationService
import com.mennad.api.association.mappers.AssociationMapper
import com.mennad.api.association.resolvers.dtos.AssociationDto
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class AssociationQueryResolver(
    val associationService: AssociationService,
): GraphQLQueryResolver {

    @QueryMapping(name= "getAssociations")
    fun getAssociations(): List<AssociationDto?> {
        return AssociationMapper.toAssociationDtos(associationService.getAssociations())
    }

    @QueryMapping(name= "getAssociation")
    fun getAssociation(@Argument id: String): AssociationDto? {
        return associationService.getAssociation(id)?.let { AssociationMapper.toAssociationDto(it) }
    }
}