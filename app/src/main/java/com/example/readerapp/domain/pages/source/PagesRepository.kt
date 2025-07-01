package com.example.readerapp.domain.pages.source

import com.example.readerapp.domain.pages.model.PageContent
import kotlinx.coroutines.flow.Flow

interface PagesRepository {

    fun getPageContent(): Flow<List<PageContent>>

    fun getPageContentImage(id: String): Flow<PageContent>
}