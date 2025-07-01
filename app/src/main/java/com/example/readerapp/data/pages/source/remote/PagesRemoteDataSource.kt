package com.example.readerapp.data.pages.source.remote

import com.example.readerapp.data.source.remote.model.pages.ApiPageContent
import kotlinx.coroutines.flow.Flow

interface PagesRemoteDataSource {

    fun getPageContent(): Flow<ApiPageContent>
}