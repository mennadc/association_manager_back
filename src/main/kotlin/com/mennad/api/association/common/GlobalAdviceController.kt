package com.mennad.api.association.common

import com.mennad.api.association.common.errors.MutationError
import com.mennad.api.association.common.errors.ValidationError
import com.mennad.api.association.common.exceptions.MutationException
import com.mennad.api.association.common.exceptions.ValidationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalAdviceController {
    @ExceptionHandler(ValidationException::class)
    fun onValidationErrorException(e: ValidationException): ResponseEntity<List<ValidationError>> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getValidationErrors())
    }

    @ExceptionHandler(MutationException::class)
    fun onMutationErrorException(e: MutationException): ResponseEntity<MutationError> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMutationError())
    }
}