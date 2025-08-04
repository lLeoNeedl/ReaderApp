package com.readerapp.readerapp.ui.pages.model

data class UiPageContent(
    val id: String,
    val kind: UiPageContentKind
)

sealed interface UiPageContentKind {

    data class Text(
        val text: String,
        val textSize: Int
    ) : UiPageContentKind

    data class Image(val url: String) : UiPageContentKind
}