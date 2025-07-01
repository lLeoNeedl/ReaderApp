package com.example.readerapp.ui.common.model

sealed class UiScreenDataResult<out T> {

    data object ContentLoading : UiScreenDataResult<Nothing>()

    data object Error : UiScreenDataResult<Nothing>()

    data class Success<T>(val data: T) : UiScreenDataResult<T>()
}