package com.example.readerapp.data.pages.source.local

import com.example.readerapp.data.source.remote.model.pages.ApiPageContent
import com.example.readerapp.domain.pages.model.PageContent
import kotlinx.coroutines.flow.Flow

interface PagesLocalDataSource {

    suspend fun savePageContent(content: ApiPageContent)

    fun getPageContent(): Flow<List<PageContent>>

    fun getPageContentImage(id: String): Flow<PageContent>
}