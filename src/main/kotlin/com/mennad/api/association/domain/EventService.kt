package com.mennad.api.association.domain

import com.mennad.api.association.common.errors.ValidationError
import com.mennad.api.association.common.exceptions.ValidationException
import com.mennad.api.association.domain.model.Event
import com.mennad.api.association.helpers.Validator
import com.mennad.api.association.repositories.AssociationRepository
import com.mennad.api.association.repositories.EventRepository
import com.mennad.api.association.repositories.UserRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.util.*

@Service
@Component
class EventService @Autowired constructor(
    val eventRepository: EventRepository,
    val associationRepository: AssociationRepository,
    val userRepository: UserRepository
){

    private val logger = KotlinLogging.logger {}

    /**
     * All event retrieval service.
     * Returns all existing events from database.
     * @return
     */
    fun getEvents(): List<Event> {
        return this.eventRepository.findAll()
    }

    /**
     * One given event retrieval service.
     * Returns existing event from database corresponding to a given id.
     * @param id
     * @return
     */
    fun getEvent(id: String): Event? {
        return this.eventRepository.findById(id).get()
    }

    /**
     * Event creation service.
     * Save valid new event in database.
     * @param event
     * @return
     */
    fun createEvent(event: Event): Event {
        validateFields(event)
        event.id = UUID.randomUUID().toString()
        debit(event.budget, event.associationId)
        return this.eventRepository.save(event)
    }

    /**
     * Check event fields for saving, matching to the following criteria :
     * - string fields are not null and not empty;
     * - event budget is strictly positive;
     * - event association id refers to an existing association;
     * - event organizer id refers to an existing user;
     * - event organizer is part of the event association.
     * @param event
     * @exception ValidationException
     */
    private fun validateFields(event: Event) {
        val validationErrors = mutableListOf<ValidationError>()

        Validator.validateStringField(event.title, "title")?.also { validationErrors += it }
        Validator.validateStringField(event.description, "description")?.also { validationErrors += it }
        Validator.validateIntegerField(event.budget, "founder")?.also { validationErrors += it }
        validateBudget(event.budget, event.associationId)?.also { validationErrors += it }
        Validator.validateStringField(event.associationId, "association id")?.also { validationErrors += it }
        validateAssociation(event.associationId)?.also { validationErrors += it }
        Validator.validateStringField(event.organizer, "organizer")?.also { validationErrors += it }
        validateOrganizer(event.organizer, event.associationId)?.also { validationErrors += it }

        if (validationErrors.isNotEmpty()) {
            throw ValidationException(validationErrors)
        }
    }

    /**
     * Returns a ValidationError instance if the given association
     * does not have enough funds for the given event budget.
     * @param budget
     * @param associationId
     * @return
     */
    private fun validateBudget(budget: Int, associationId: String): ValidationError? {
        val association = associationRepository.findById(associationId).get()
        return if (association.funds - budget < 0) {
            ValidationError("Event association has not enough funds to finance event")
        } else null
    }

    /**
     * Returns a ValidationError instance if the given association id
     * does not correspond to an existing association, otherwise null.
     * @param associationId
     * @return
     */
    private fun validateAssociation(associationId: String): ValidationError? {
        return if (!this.associationRepository.existsById(associationId)) ValidationError("User association does not exist") else null
    }

    /**
     * Returns a ValidationError instance if the given organizer does not exist
     * or if the organizer's association is not part of the given event association.
     * @param organizer
     * @param associationId
     * @return
     */
    private fun validateOrganizer(organizer: String, associationId: String): ValidationError? {
        val user = userRepository.findById(organizer).get() 
        return if (user.associationId != associationId) {
            ValidationError("Event organizer does not adhere to event association")
        } else null
    }

    /**
     * Updates the given association funds amount
     * by debiting the event given budget.
     * @param budget
     * @param associationId
     * @return
     */
    fun debit(budget: Int, associationId: String) {
        val association = associationRepository.findById(associationId).get()
        association.funds -= budget
        associationRepository.save(association)
    }
}