package com.example.readerapp.ui.common.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.readerapp.R

@Composable
fun AppContentPlaceholder(
    modifier: Modifier = Modifier,
    painter: Painter,
    contentDescription: String? = null,
    painterTint: Color? = MaterialTheme.colorScheme.onSurface,
    text: String,
    withRetry: Boolean = false,
    onRetryClick: () -> Unit = {}
) {

    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier
                .size(200.dp),
            painter = painter,
            contentDescription = contentDescription,
            colorFilter = if (painterTint != null) {
                ColorFilter.tint(color = painterTint)
            } else {
                null
            }
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge
        )

        if (withRetry) {

            Spacer(Modifier.height(36.dp))

            AppButton(
                text = stringResource(R.string.retry),
                onClick = onRetryClick
            )
        }
    }
}

@Composable
fun AppLoadingState(
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        AppCircularProgressIndicator(
            modifier = Modifier
                .size(48.dp)
        )
    }
}

@Composable
fun AppCircularProgressIndicator(
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 5.dp,
    color: Color = MaterialTheme.colorScheme.primary
) {
    CircularProgressIndicator(
        modifier = modifier,
        strokeWidth = strokeWidth,
        color = color,
    )
}