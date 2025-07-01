package com.example.readerapp.ui.common.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.readerapp.R
import com.example.readerapp.ui.common.model.UiLoadingState

@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    loadingState: UiLoadingState,
    topBar: @Composable () -> Unit = {},
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainerLowest,
    onContentLoadingRetryClick: () -> Unit = {},
    contentWindowInsets: WindowInsets = WindowInsets(0, 0, 0, 0),
    content: @Composable (PaddingValues) -> Unit
) {

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = topBar,
        containerColor = containerColor,
        contentWindowInsets = contentWindowInsets
    ) { paddingValues ->

        when (loadingState) {

            is UiLoadingState.ContentLoading -> {
                AppLoadingState(
                    modifier = Modifier
                        .fillMaxSize()
                )
            }

            is UiLoadingState.ContentLoadingError -> {
                AppContentPlaceholder(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = painterResource(R.drawable.ic_error),
                    text = stringResource(R.string.error_default),
                    withRetry = true,
                    onRetryClick = onContentLoadingRetryClick
                )
            }

            is UiLoadingState.Content -> content(paddingValues)
        }
    }
}