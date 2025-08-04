package com.readerapp.readerapp.ui.common.composables

import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.readerapp.readerapp.R

@Composable
fun AppAsyncImage(
    modifier: Modifier = Modifier,
    url: String,
    contentDescription: String? = null,
    placeholder: Painter = painterResource(R.drawable.ic_placeholder_default),
    placeholderTint: Color? = MaterialTheme.colorScheme.onSurface,
    contentScale: ContentScale = ContentScale.Crop,
) {

    val painter = rememberAsyncImagePainter(url)
    val state by painter.state.collectAsState()

    when (state) {

        is AsyncImagePainter.State.Success -> {

            Image(
                modifier = modifier,
                painter = painter,
                contentDescription = contentDescription,
                contentScale = contentScale
            )
        }

        else -> {

            Image(
                modifier = modifier,
                painter = placeholder,
                contentDescription = contentDescription,
                contentScale = contentScale,
                colorFilter = if (placeholderTint != null) {
                    ColorFilter.tint(color = placeholderTint)
                } else {
                    null
                },
            )
        }
    }
}