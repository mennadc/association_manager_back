package com.mennad.api.association.resolvers

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.mennad.api.association.domain.EventService
import com.mennad.api.association.mappers.EventMapper
import com.mennad.api.association.resolvers.dtos.EventDto
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class EventQueryResolver(
    val eventService: EventService,
): GraphQLQueryResolver {

    @QueryMapping(name= "getEvents")
    fun getEvents(): List<EventDto?> {
        return EventMapper.toEventDtos(eventService.getEvents())
    }

    @QueryMapping(name= "getEvent")
    fun getEvent(@Argument id: String): EventDto? {
        return eventService.getEvent(id)?.let { EventMapper.toEventDto(it) }
    }
}