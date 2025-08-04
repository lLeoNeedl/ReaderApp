package com.readerapp.readerapp.data.pages.source.remote

import com.readerapp.readerapp.data.source.remote.ApiClient
import com.readerapp.readerapp.data.source.remote.model.pages.ApiPageContent
import kotlinx.coroutines.flow.Flow

class PagesRemoteDataSourceImpl(
    private val apiClient: ApiClient
) : PagesRemoteDataSource {

    override fun getPageContent(): Flow<ApiPageContent> {
        return apiClient.getPageContent()
    }
}