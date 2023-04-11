package com.mennad.api.association.repositories

import com.mennad.api.association.domain.model.Event
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface EventRepository : MongoRepository<Event, String> {

    @Query(value="{organizer : $0}", delete = true)
    fun deleteByOrganizer(organizer: String)
}