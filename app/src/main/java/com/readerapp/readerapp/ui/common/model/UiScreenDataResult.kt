package com.readerapp.readerapp.ui.common.model

sealed interface UiScreenDataResult<out T> {

    data object ContentLoading : UiScreenDataResult<Nothing>

    data object Error : UiScreenDataResult<Nothing>

    data class Success<T>(val data: T) : UiScreenDataResult<T>
}