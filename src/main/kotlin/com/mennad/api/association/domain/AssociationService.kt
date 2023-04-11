package com.mennad.api.association.domain

import com.mennad.api.association.common.errors.ValidationError
import com.mennad.api.association.common.exceptions.ValidationException
import com.mennad.api.association.domain.model.Association
import com.mennad.api.association.helpers.Validator
import com.mennad.api.association.repositories.AssociationRepository
import com.mennad.api.association.repositories.UserRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.util.*

@Service
@Component
class AssociationService @Autowired constructor(
    val associationRepository: AssociationRepository,
    val userRepository: UserRepository
){

    private val logger = KotlinLogging.logger {}

    /**
     * All association retrieval service.
     * Returns all existing associations from database.
     * @return
     */
    fun getAssociations(): List<Association> {
        return this.associationRepository.findAll()
    }

    /**
     * One given association retrieval service.
     * Returns existing association from database corresponding to a given id.
     * @param id
     * @return
     */
    fun getAssociation(id: String): Association? {
        return this.associationRepository.findById(id).get()
    }


    /**
     * Association creation service.
     * Save valid new association in database.
     * @param association
     * @return
     */
    fun createAssociation(association: Association): Association {
        validateFields(association)
        association.id = UUID.randomUUID().toString()
        return this.associationRepository.save(association)
    }

    /**
     * Check association fields for saving, matching to the following criteria :
     * - string fields are not null and not empty;
     * - association funds is strictly positive;
     * - association founder refers to an existing user.
     * @param association
     * @exception ValidationException
     */
    private fun validateFields(association: Association) {
        val validationErrors = mutableListOf<ValidationError>()

        Validator.validateStringField(association.name, "name")?.let { validationErrors += it }
        Validator.validateStringField(association.activity, "activity")?.let { validationErrors += it }
        Validator.validateIntegerField(association.funds, "funds")?.let { validationErrors += it }
        Validator.validateStringField(association.founder, "founder")?.let { validationErrors += it }
        validateFounder(association.founder)?.let { validationErrors += it }

        if (validationErrors.isNotEmpty()) {
            throw ValidationException(validationErrors)
        }
    }

    /**
     * Returns a ValidationError instance if the given founder id
     * does not correspond to an existing user, otherwise null.
     * @param founder
     * @return
     */
    private fun validateFounder(founder: String): ValidationError? {
        return if (!userRepository.existsById(founder)) {
            ValidationError("Association founder doesn't exist")
        } else null
    }
}