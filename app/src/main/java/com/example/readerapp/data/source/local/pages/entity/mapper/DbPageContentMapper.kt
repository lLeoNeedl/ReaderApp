package com.example.readerapp.data.source.local.pages.entity.mapper

import com.example.readerapp.data.source.local.pages.entity.DbPageContent
import com.example.readerapp.data.source.local.pages.entity.DbPageContentImage
import com.example.readerapp.data.source.local.pages.entity.DbPageContentFull
import com.example.readerapp.data.source.remote.model.pages.mapper.PageContentFlattened
import com.example.readerapp.domain.pages.model.PageContent
import com.example.readerapp.domain.pages.model.PageContentKind
import com.example.readerapp.domain.pages.model.PageContentType

object DbPageContentMapper {

    fun toPageContent(
        dbContent: List<DbPageContentFull>
    ): List<PageContent> {

        return dbContent.map(::toPageContent)
    }

    fun toPageContent(
        dbContent: DbPageContentFull
    ): PageContent {

        val type = PageContentType.find(typeId = dbContent.type)

        val content = when (type) {
            PageContentType.Page -> PageContentKind.Page
            PageContentType.Section -> PageContentKind.Section
            PageContentType.Text -> PageContentKind.Text
            PageContentType.Image -> PageContentKind.Image(url = dbContent.imageUrl)
            PageContentType.Unsupported -> error("Unsupported page content type must be filtered out")
        }

        return with(dbContent) {

            PageContent(
                id = id,
                text = text,
                depth = depth,
                kind = content
            )
        }
    }

    fun toDbPageContent(
        contentFlattened: PageContentFlattened,
        orderPosition: Int,
    ): DbPageContent {

        return with(contentFlattened) {

            DbPageContent(
                id = id,
                type = type,
                text = text,
                depth = depth,
                orderPosition = orderPosition
            )
        }
    }

    fun toDbPageContentImage(
        contentFlattened: PageContentFlattened,
    ): DbPageContentImage {

        return with(contentFlattened) {
            DbPageContentImage(
                id = id,
                url = imageUrl
            )
        }
    }
}