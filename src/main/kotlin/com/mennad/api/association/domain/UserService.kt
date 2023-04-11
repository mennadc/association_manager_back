package com.mennad.api.association.domain

import com.mennad.api.association.common.errors.ValidationError
import com.mennad.api.association.common.exceptions.MutationException
import com.mennad.api.association.common.exceptions.ValidationException
import com.mennad.api.association.domain.model.User
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
class UserService @Autowired constructor(
    private val userRepository: UserRepository,
    private val associationRepository: AssociationRepository,
    private val eventRepository: EventRepository
) {

    private val logger = KotlinLogging.logger {}

    /**
     * All user retrieval service.
     * Returns all existing users from database.
     * @return
     */
    fun getUsers(): List<User> {
        return this.userRepository.findAll()
    }

    /**
     * One given user retrieval service.
     * Returns existing user from database corresponding to a given id.
     * @param id
     * @return
     */
    fun getUser(id: String): User? {
        return this.userRepository.findById(id).get()
    }

    /**
     * User creation service.
     * Save valid new user in database.
     * @param user
     * @return
     */
    fun createUser(user: User): User {
        validateFields(user)
        user.id = UUID.randomUUID().toString()
        return this.userRepository.save(user)
    }

    /**
     * One given user deletion service.
     * Returns true if the user has been deleted from database,
     * removing all event referencing the user, otherwise false.
     * @param id
     * @return
     */
    fun deleteUser(id: String): Boolean {
        eventRepository.deleteByOrganizer(id)
        userRepository.deleteById(id)
        return !userRepository.existsById(id)
    }

    /**
     * One given user update service.
     * Returns the user with updated fields.
     * New Field are validated before saving.
     * @param id
     * @param updatedUser
     * @return
     * @exception MutationException
     */
    fun updateUser(id: String, updatedUser: User): User {
        val user = this.getUser(id) ?: throw MutationException("User does not exist")

        updatedUser.id = id
        updatedUser.firstName = updatedUser.firstName.ifEmpty { user.firstName }
        updatedUser.lastName = updatedUser.lastName.ifEmpty { user.lastName }
        updatedUser.userName = updatedUser.userName.ifEmpty { user.userName }
        updatedUser.email = updatedUser.email.ifEmpty { user.email }
        updatedUser.password = updatedUser.password.ifEmpty { user.password }
        updatedUser.wallet = if (updatedUser.wallet < 0) user.wallet else updatedUser.wallet
        updatedUser.associationId = updatedUser.associationId.ifEmpty { user.associationId }

        validateFields(updatedUser)
        return userRepository.save(updatedUser)
    }

    /**
     * Check user fields for saving, matching to the following criteria :
     * - string fields are not null and not empty;
     * - email follows the
     * - user's wallet is strictly positive;
     * - user's association id refers to an existing association.
     * @param user
     * @exception ValidationException
     */
    private fun validateFields(user: User) {
        val validationErrors = mutableListOf<ValidationError>()

        Validator.validateStringField(user.firstName, "firstname")?.let { validationErrors += it }
        Validator.validateStringField(user.lastName, "lastname")?.let { validationErrors += it }
        Validator.validateStringField(user.userName, "username")?.let { validationErrors += it }
        Validator.validateStringField(user.email, "email")?.let { validationErrors += it }
        Validator.validateEmail(user.email)?.let { validationErrors += it }
        Validator.validateStringField(user.password, "password")?.let { validationErrors += it }
        Validator.validateIntegerField(user.wallet, "wallet")?.let { validationErrors += it }
        validateAssociation(user.associationId)?.let { validationErrors += it }

        if (validationErrors.isNotEmpty()) {
            throw ValidationException(validationErrors)
        }
    }

    /**
     * Returns a ValidationError instance if the given association id
     * does not correspond to an existing association, otherwise null.
     * @param associationId
     * @return
     */
    private fun validateAssociation(associationId: String): ValidationError? {
        return if (associationId.isNotEmpty()) {
            if (!associationRepository.existsById(associationId)) ValidationError("User association does not exist") else null
        } else null
    }
}
