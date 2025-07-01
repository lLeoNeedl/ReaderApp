package com.example.readerapp.ui.pages.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readerapp.domain.pages.model.PageContent
import com.example.readerapp.domain.pages.usecases.GetPageContentUseCase
import com.example.readerapp.ui.common.model.UiDataLoadEvent
import com.example.readerapp.ui.common.model.UiLoadingState
import com.example.readerapp.ui.common.model.UiScreenDataResult
import com.example.readerapp.ui.pages.model.UiPageContent
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

data class PagesScreenState(
    val loadingState: UiLoadingState = UiLoadingState.ContentLoading,
    val content: List<UiPageContent> = emptyList()
)

private data class PagesScreenData(
    val content: List<PageContent>
)

class PagesViewModel(
    getPageContentUseCase: GetPageContentUseCase
) : ViewModel() {

    private val dataLoadEvent = MutableSharedFlow<UiDataLoadEvent>()

    private val screenData = dataLoadEvent
        .onStart {
            emit(UiDataLoadEvent())
        }
        .flatMapLatest { event ->

            getPageContentUseCase()
                .map<List<PageContent>, UiScreenDataResult<PagesScreenData>> { content ->
                    UiScreenDataResult.Success(
                        data = PagesScreenData(
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
            PagesScreenState()
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

    private fun processData(screenDataResult: UiScreenDataResult<PagesScreenData>): PagesScreenState {

        return when (screenDataResult) {

            is UiScreenDataResult.ContentLoading -> {
                PagesScreenState(
                    loadingState = UiLoadingState.ContentLoading,
                )
            }

            is UiScreenDataResult.Error -> {
                PagesScreenState(
                    loadingState = UiLoadingState.ContentLoadingError
                )
            }

            is UiScreenDataResult.Success -> {
                PagesScreenState(
                    loadingState = UiLoadingState.Content,
                    content = UiPageContentMapper.toUiPageContent(
                        screenDataResult.data.content
                    )
                )
            }
        }
    }
}