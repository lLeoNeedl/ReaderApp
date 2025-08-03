package com.readerapp.readerapp.data.pages.source.remote

import com.readerapp.readerapp.data.source.remote.model.pages.ApiPageContent
import kotlinx.coroutines.flow.Flow

interface PagesRemoteDataSource {

    fun getPageContent(): Flow<ApiPageContent>
}