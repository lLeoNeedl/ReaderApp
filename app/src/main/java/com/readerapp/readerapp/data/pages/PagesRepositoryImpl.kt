package com.readerapp.readerapp.data.pages

import com.readerapp.readerapp.domain.dataload.DataLoadConfig
import com.readerapp.readerapp.data.pages.source.local.PagesLocalDataSource
import com.readerapp.readerapp.data.pages.source.remote.PagesRemoteDataSource
import com.readerapp.readerapp.data.utils.dataLoadFlow
import com.readerapp.readerapp.domain.pages.model.PageContent
import com.readerapp.readerapp.domain.pages.source.PagesRepository
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