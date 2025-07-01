package com.example.readerapp.data.pages

import com.example.readerapp.data.common.DataLoadConfig
import com.example.readerapp.data.pages.source.local.PagesLocalDataSource
import com.example.readerapp.data.pages.source.remote.PagesRemoteDataSource
import com.example.readerapp.data.utils.dataLoadFlow
import com.example.readerapp.domain.pages.model.PageContent
import com.example.readerapp.domain.pages.source.PagesRepository
import kotlinx.coroutines.flow.Flow

class PagesRepositoryImpl(
    private val localDataSource: PagesLocalDataSource,
    private val remoteDataSource: PagesRemoteDataSource
) : PagesRepository {

    override fun getPageContent(): Flow<List<PageContent>> {

        return dataLoadFlow(
            getLocalData = localDataSource.getPageContent(),
            getRemoteData = remoteDataSource.getPageContent(),
            saveRemoteData = localDataSource::savePageContent,
        )
    }

    override fun getPageContentImage(id: String): Flow<PageContent> {

        return dataLoadFlow<PageContent, Unit>(
            getLocalData = localDataSource.getPageContentImage(id = id),
            dataLoadConfig = DataLoadConfig.LocalOnly
        )
    }
}