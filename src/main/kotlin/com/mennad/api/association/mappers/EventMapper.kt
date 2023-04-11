package com.mennad.api.association.mappers

import com.mennad.api.association.domain.model.Event
import com.mennad.api.association.resolvers.dtos.EventDto

object EventMapper {

    fun toEvent(eventDto: EventDto): Event {
        return Event(
            eventDto.title ?: "",
            eventDto.description ?: "",
            eventDto.budget ?: -1,
            eventDto.associationId ?: "",
            eventDto.organizer ?: ""
        )
    }

    fun toEventDto(event: Event): EventDto {
        return EventDto(
            event.id,
            event.title,
            event.description,
            event.budget,
            event.associationId,
            event.organizer
        )
    }

    fun toEventDtos(events: List<Event>): List<EventDto> {
        return events.map(EventMapper::toEventDto).toList()
    }
}