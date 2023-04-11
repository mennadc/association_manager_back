package com.mennad.api.association.resolvers

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.mennad.api.association.domain.EventService
import com.mennad.api.association.mappers.EventMapper
import com.mennad.api.association.resolvers.dtos.EventDto
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class EventMutationResolver (
    val eventService: EventService,
): GraphQLMutationResolver {

    @SchemaMapping(typeName = "Mutation", field = "createEvent")
    fun createEvent(@Argument eventDto: EventDto): EventDto {
        return EventMapper.toEventDto(eventService.createEvent(EventMapper.toEvent(eventDto)))
    }
}

