package com.readerapp.readerapp.ui.common.model

sealed interface UiLoadingState {

    data object ContentLoading : UiLoadingState

    data object ContentLoadingError : UiLoadingState

    data object Content : UiLoadingState
}