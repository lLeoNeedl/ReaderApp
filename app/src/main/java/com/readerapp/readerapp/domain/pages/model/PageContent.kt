package com.readerapp.readerapp.domain.pages.model

/**
 * @param depth indicates the level of nesting of a specific content is in the hierarchy (aka number of parents)
 */
data class PageContent(
    val id: String,
    val text: String,
    val depth: Int,
    val kind: PageContentKind
)

sealed interface PageContentKind {

    data object Page : PageContentKind

    data object Section : PageContentKind

    data object Text : PageContentKind

    data class Image(val url: String?) : PageContentKind
}