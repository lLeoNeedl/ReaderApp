package com.readerapp.readerapp.ui.pages.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.readerapp.readerapp.R
import com.readerapp.readerapp.ui.common.composables.AppAsyncImage
import com.readerapp.readerapp.ui.common.composables.AppContentPlaceholder
import com.readerapp.readerapp.ui.common.composables.AppScaffold
import com.readerapp.readerapp.ui.pages.model.UiPageContent
import com.readerapp.readerapp.ui.pages.model.UiPageContentKind

@Composable
fun PagesScreen(
    viewModel: PagesViewModel,
    onImageClick: (UiPageContent) -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    PagesScreen(
        state = state,
        onRetryClick = viewModel::onRetryClick,
        onImageClick = onImageClick
    )
}

@Composable
private fun PagesScreen(
    state: PagesScreenState,
    onRetryClick: () -> Unit,
    onImageClick: (UiPageContent) -> Unit
) {

    AppScaffold(
        loadingState = state.loadingState,
        onContentLoadingRetryClick = onRetryClick,
    ) { paddingValues ->

        PagesScreenContent(
            paddingValues = paddingValues,
            state = state,
            onImageClick = onImageClick,
        )
    }
}

@Composable
private fun PagesScreenContent(
    paddingValues: PaddingValues,
    state: PagesScreenState,
    onImageClick: (UiPageContent) -> Unit
) {

    if (state.content.isNotEmpty()) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(
                start = 16.dp,
                top = WindowInsets.systemBars.asPaddingValues().calculateTopPadding() + 24.dp,
                end = 16.dp,
                bottom = WindowInsets.systemBars.asPaddingValues().calculateBottomPadding() + 24.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            items(
                items = state.content
            ) { content ->

                PageContent(
                    content = content,
                    onImageClick = {
                        onImageClick(content)
                    }
                )
            }
        }
    } else {

        AppContentPlaceholder(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(R.drawable.ic_placeholder_default),
            text = stringResource(R.string.empty_content)
        )
    }
}

@Composable
private fun PageContent(
    modifier: Modifier = Modifier,
    content: UiPageContent,
    onImageClick: () -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxWidth(),
    ) {

        with(content) {

            when (kind) {

                is UiPageContentKind.Text -> {

                    Text(
                        text = kind.text,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = kind.textSize.sp,
                    )
                }

                is UiPageContentKind.Image -> {

                    Box(
                        modifier = Modifier
                            .clickable(
                                onClick = onImageClick
                            )
                            .padding(16.dp)
                    ) {

                        AppAsyncImage(
                            modifier = Modifier
                                .size(100.dp),
                            url = kind.url,
                        )
                    }
                }
            }
        }
    }
}

