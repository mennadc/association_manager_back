package com.mennad.api.association.mappers

import com.mennad.api.association.domain.model.Association
import com.mennad.api.association.resolvers.dtos.AssociationDto

object AssociationMapper {

    fun toAssociation(associationDto: AssociationDto): Association {
        return Association(
            associationDto.name ?: "",
            associationDto.activity ?: "",
            associationDto.funds ?: -1,
            associationDto.founder ?: ""
        )
    }

    fun toAssociationDto(association: Association): AssociationDto {
        return AssociationDto(
            association.id,
            association.name,
            association.activity,
            association.funds,
            association.founder
        )
    }

    fun toAssociationDtos(associations: List<Association>): List<AssociationDto> {
        return associations.map(AssociationMapper::toAssociationDto).toList()
    }
}