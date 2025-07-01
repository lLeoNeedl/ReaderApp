package com.example.readerapp.ui.common.model

sealed class UiLoadingState {

    data object ContentLoading : UiLoadingState()

    data object ContentLoadingError : UiLoadingState()

    data object Content : UiLoadingState()
}
