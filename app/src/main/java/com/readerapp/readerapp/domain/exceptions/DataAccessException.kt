package com.readerapp.readerapp.domain.exceptions

sealed class DataAccessException(override val message: String) : Throwable(message = message) {

    /**
     * Default exception for cases when the error is not specified.
     */
    data class Unknown(override val message: String) : DataAccessException(message = message)
}