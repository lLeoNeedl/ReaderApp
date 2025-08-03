package com.readerapp.readerapp.data.pages.source.local

import com.readerapp.readerapp.data.source.remote.model.pages.ApiPageContent
import com.readerapp.readerapp.domain.pages.model.PageContent
import kotlinx.coroutines.flow.Flow

interface PagesLocalDataSource {

    suspend fun savePageContent(content: ApiPageContent)

    fun getPageContent(): Flow<List<PageContent>>

    fun getPageContentImage(id: String): Flow<PageContent>
}