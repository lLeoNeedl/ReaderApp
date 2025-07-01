package com.example.readerapp.ui.pages.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.readerapp.domain.pages.model.PageContent
import com.example.readerapp.domain.pages.usecases.GetPageContentImageUseCase
import com.example.readerapp.ui.common.model.UiDataLoadEvent
import com.example.readerapp.ui.common.model.UiLoadingState
import com.example.readerapp.ui.common.model.UiScreenDataResult
import com.example.readerapp.ui.pages.model.UiImageContent
import com.example.readerapp.ui.pages.model.UiPageContentMapper
import com.example.readerapp.ui.utils.flow.AppFlowConstants
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ImageDetailsScreenState(
    val loadingState: UiLoadingState = UiLoadingState.ContentLoading,
    val content: UiImageContent? = null
)

private data class ImageDetailsScreenData(
    val content: PageContent
)

class ImageDetailsViewModel(
    handle: SavedStateHandle,
    getPageContentImageUseCase: GetPageContentImageUseCase
) : ViewModel() {

    private val route: ImageDetailsRoute = handle.toRoute()

    private val dataLoadEvent = MutableSharedFlow<UiDataLoadEvent>()

    private val screenData = dataLoadEvent
        .onStart {
            emit(UiDataLoadEvent())
        }
        .flatMapLatest { event ->

            getPageContentImageUseCase(id = route.id)
                .map<PageContent, UiScreenDataResult<ImageDetailsScreenData>> { content ->
                    UiScreenDataResult.Success(
                        data = ImageDetailsScreenData(
                            content = content
                        )
                    )
                }
                .onStart {
                    if (event.isRetry) {
                        emit(UiScreenDataResult.ContentLoading)
                    }
                }
                .catch { throwable ->

                    throwable.printStackTrace()

                    emit(UiScreenDataResult.Error)
                }
        }

    val state = screenData
        .map(::processData)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(AppFlowConstants.WHILE_SUBSCRIBED_STOP_DEFAULT_MILLIS),
            ImageDetailsScreenState()
        )

    fun onRetryClick() {
        viewModelScope.launch {
            dataLoadEvent.emit(
                UiDataLoadEvent(
                    isRetry = true
                )
            )
        }
    }

    private fun processData(screenDataResult: UiScreenDataResult<ImageDetailsScreenData>): ImageDetailsScreenState {

        return when (screenDataResult) {

            is UiScreenDataResult.ContentLoading -> {
                ImageDetailsScreenState(
                    loadingState = UiLoadingState.ContentLoading,
                )
            }

            is UiScreenDataResult.Error -> {
                ImageDetailsScreenState(
                    loadingState = UiLoadingState.ContentLoadingError
                )
            }

            is UiScreenDataResult.Success -> {
                ImageDetailsScreenState(
                    loadingState = UiLoadingState.Content,
                    content = UiPageContentMapper.toUiImageContent(
                        pageContent = screenDataResult.data.content
                    )
                )
            }
        }
    }
}