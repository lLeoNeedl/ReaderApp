package com.readerapp.readerapp.ui.pages.model

import com.readerapp.readerapp.domain.pages.model.PageContent
import com.readerapp.readerapp.domain.pages.model.PageContentKind

object UiPageContentMapper {

    fun toUiPageContent(pageContent: List<PageContent>): List<UiPageContent> {

        return pageContent.map(::toUiPageContent)
    }

    fun toUiImageContent(pageContent: PageContent): UiImageContent {

        require(pageContent.kind is PageContentKind.Image)

        return UiImageContent(
            title = pageContent.text,
            url = pageContent.kind.url.orEmpty()
        )
    }

    private fun toUiPageContent(pageContent: PageContent): UiPageContent {

        return with(pageContent) {

            val contentKind = when (kind) {

                is PageContentKind.Page -> {

                    // Assuming there are 3 levels of depth
                    val textSize = when (depth) {
                        0 -> 40
                        1 -> 32
                        else -> 28
                    }

                    UiPageContentKind.Text(
                        text = text,
                        textSize = textSize
                    )
                }

                is PageContentKind.Section -> {

                    val textSize = when (depth) {
                        1 -> 28
                        2 -> 20
                        else -> 16
                    }

                    UiPageContentKind.Text(
                        text = text,
                        textSize = textSize
                    )
                }

                is PageContentKind.Text -> {

                    UiPageContentKind.Text(
                        text = text,
                        textSize = 14
                    )
                }

                is PageContentKind.Image -> {

                    UiPageContentKind.Image(
                        url = kind.url.orEmpty()
                    )
                }
            }

            UiPageContent(
                id = id,
                kind = contentKind
            )
        }
    }
}