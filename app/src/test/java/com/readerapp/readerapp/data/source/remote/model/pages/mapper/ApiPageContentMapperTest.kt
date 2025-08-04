package com.readerapp.readerapp.data.source.remote.model.pages.mapper

import com.readerapp.readerapp.data.source.remote.model.pages.ApiPageContent
import com.readerapp.readerapp.domain.pages.model.PageContentType
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ApiPageContentMapperTest {

    private val text = "Root"
    private val imageUrl = "imageUrl"

    private val parentText = "Parent"
    private val childText = "Child"

    @Test
    fun flattenContentWithNullItems() {

        val type = PageContentType.Image.id

        val content = ApiPageContent(
            text = text,
            type = type,
            imageUrl = imageUrl
        )

        val result = ApiPageContentMapper.flatten(
            apiPageContent = content,
            depth = 0
        )

        val id = ApiPageContentMapper.createId(content.hashCode())

        assertEquals(1, result.size)
        assertEquals(id, result.first().id)
        assertEquals(type, result.first().type)
        assertEquals(text, result.first().text)
        assertEquals(0, result.first().depth)
        assertEquals(imageUrl, result.first().imageUrl)
    }

    @Test
    fun flattenContentWithEmptyItems() {

        val type = PageContentType.Page.id

        val content = ApiPageContent(
            text = text,
            type = PageContentType.Page.id,
            items = emptyList()
        )

        val result = ApiPageContentMapper.flatten(
            apiPageContent = content,
            depth = 0
        )

        val id = ApiPageContentMapper.createId(content.hashCode())

        assertEquals(1, result.size)
        assertEquals(id, result.first().id)
        assertEquals(type, result.first().type)
        assertEquals(text, result.first().text)
        assertEquals(0, result.first().depth)
    }

    @Test
    fun flattenContentWithChildItems() {

        val childType = PageContentType.Text.id

        val child = ApiPageContent(
            text = childText,
            type = childType,
        )

        val parentType = PageContentType.Section.id

        val parent = ApiPageContent(
            text = parentText,
            type = parentType,
            items = listOf(child)
        )

        val result = ApiPageContentMapper.flatten(
            apiPageContent = parent,
            depth = 0
        )

        assertEquals(2, result.size)

        val parentId = ApiPageContentMapper.createId(parent.hashCode())
        val childId = ApiPageContentMapper.createId(child.hashCode())

        val parentResult = result[0]
        val childResult = result[1]

        assertEquals(parentId, parentResult.id)
        assertEquals(parentType, parentResult.type)
        assertEquals(parentText, parentResult.text)
        assertEquals(0, parentResult.depth)

        assertEquals(childId, childResult.id)
        assertEquals(childType, childResult.type)
        assertEquals(childText, childResult.text)
        assertEquals(1, childResult.depth)
    }
}