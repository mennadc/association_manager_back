package com.mennad.api.association.resolvers

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.mennad.api.association.domain.AssociationService
import com.mennad.api.association.mappers.AssociationMapper
import com.mennad.api.association.resolvers.dtos.AssociationDto
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class AssociationMutationResolver (
    val associationService: AssociationService,
): GraphQLMutationResolver {

    @SchemaMapping(typeName = "Mutation", field = "createAssociation")
    fun createAssociation(@Argument associationDto: AssociationDto): AssociationDto {
        return AssociationMapper.toAssociationDto(associationService.createAssociation(AssociationMapper.toAssociation(associationDto)))
    }
}

