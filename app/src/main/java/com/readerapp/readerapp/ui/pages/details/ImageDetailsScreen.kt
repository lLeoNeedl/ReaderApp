package com.readerapp.readerapp.ui.pages.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.readerapp.readerapp.ui.common.composables.AppAsyncImage
import com.readerapp.readerapp.ui.common.composables.AppScaffold
import com.readerapp.readerapp.ui.common.composables.AppTopAppBar

@Composable
fun ImageDetailsScreen(
    viewModel: ImageDetailsViewModel,
    onBackClick: () -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    ImageDetailsScreen(
        state = state,
        onBackClick = onBackClick,
        onRetryClick = viewModel::onRetryClick
    )
}

@Composable
private fun ImageDetailsScreen(
    state: ImageDetailsScreenState,
    onBackClick: () -> Unit,
    onRetryClick: () -> Unit
) {

    AppScaffold(
        loadingState = state.loadingState,
        topBar = {
            AppTopAppBar(
                title = "",
                onNavigationIconClick = onBackClick
            )
        },
        onContentLoadingRetryClick = onRetryClick,
        contentWindowInsets = WindowInsets.navigationBars
    ) { paddingValues ->

        ImageDetailsScreenContent(
            paddingValues = paddingValues,
            state = state,
        )
    }
}

@Composable
fun ImageDetailsScreenContent(
    paddingValues: PaddingValues,
    state: ImageDetailsScreenState,
) {

    requireNotNull(state.content)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
            .padding(
                start = 16.dp,
                top = 16.dp,
                end = 16.dp,
                bottom = 24.dp
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        AppAsyncImage(
            modifier = Modifier
                .fillMaxWidth(),
            url = state.content.url,
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = state.content.title,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.headlineMedium,
        )
    }
}