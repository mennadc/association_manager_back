package com.mennad.api.association.common.exceptions

import com.mennad.api.association.common.errors.MutationError

class MutationException(message: String) : RuntimeException() {

    private val mutationError: MutationError

    init {
        this.mutationError = MutationError(message)
    }

    fun getMutationError(): MutationError {
        return mutationError
    }
}
