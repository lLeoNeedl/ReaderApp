package com.readerapp.readerapp.data.source.remote.model.pages.mapper

import androidx.annotation.VisibleForTesting
import com.readerapp.readerapp.data.source.remote.model.pages.ApiPageContent
import kotlin.math.absoluteValue

// Intermediate data class to ease mapping ApiPageContent to another structures,
// e.g. db model
data class PageContentFlattened(
    val id: String,
    val type: String,
    val text: String,
    val depth: Int,
    val imageUrl: String? = null,
)

object ApiPageContentMapper {

    // The idea is to flatten the nested structure of ApiPageContent, so it can be easily processed further.
    // Current logic was designed to allow fetching all Page content or simple children, which don't
    // have nested children themselves (Text, Images).
    // If we want to work with the children that have their own children, e.g., Sections
    // (for example, fetch them from DB) we can create additional field for every flattened object
    // composed from parent item ids (it can be named hierarchyPath), which will look like this:
    // parentId_childId_anotherChildId. We can compose hierarchyPath here.
    fun flatten(
        apiPageContent: ApiPageContent,
        depth: Int,
    ): List<PageContentFlattened> {

        val rootContent = apiPageContent.map(
            id = createId(apiPageContent.hashCode()),
            depth = depth
        )

        val childContent = apiPageContent.items?.flatMap { content ->
            flatten(
                apiPageContent = content,
                depth = depth + 1,
            )
        }.orEmpty()

        return listOf(rootContent) + childContent
    }

    fun ApiPageContent.map(id: String, depth: Int): PageContentFlattened {

        return PageContentFlattened(
            id = id,
            text = text,
            type = type,
            depth = depth,
            imageUrl = imageUrl,
        )
    }

    @VisibleForTesting
    internal fun createId(hashCode: Int): String = hashCode.absoluteValue.toString()
}