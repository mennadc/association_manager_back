package com.mennad.api.association.helpers

import com.mennad.api.association.common.errors.ValidationError

object Validator {

    private val EMAIL_PATTERN = Regex("[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}")

    /**
     * Returns a ValidationError instance if the given email is valid,
     * otherwise null.
     * @param email
     * @return
     */
    fun validateEmail(email: String): ValidationError? {
        return if (!EMAIL_PATTERN.matches(email)) ValidationError("Invalid email") else null
    }

    /**
     * Returns a ValidationError instance if a given field is null or empty,
     * otherwise null.
     * @param field
     * @param fieldName
     * @return
     */
    fun validateStringField(field: String?, fieldName: String): ValidationError? {
        if (field == null) {
            return ValidationError("$fieldName is null")
        }
        return if (field.isEmpty()) {
            ValidationError("$fieldName is empty")
        } else null
    }

    /**
     * Returns a ValidationError instance if the given integer field is null
     * or negative otherwise null.
     * @param field
     * @param fieldName
     * @return
     */
    fun validateIntegerField(field: Int?, fieldName: String): ValidationError? {
        if (field == null) {
            return ValidationError("$fieldName is null")
        }
        return if (field < 0) {
            ValidationError("$fieldName is negative")
        } else null
    }
}