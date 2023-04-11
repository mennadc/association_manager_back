package com.mennad.api.association.common.exceptions

import com.mennad.api.association.common.errors.ValidationError

class ValidationException(private val validationErrors: List<ValidationError>) : RuntimeException() {

    fun getValidationErrors(): List<ValidationError> {
        return validationErrors
    }
}