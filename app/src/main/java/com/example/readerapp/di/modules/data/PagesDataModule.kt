package com.example.readerapp.di.modules.data

import com.example.readerapp.data.pages.source.remote.PagesRemoteDataSource
import com.example.readerapp.data.pages.source.remote.PagesRemoteDataSourceImpl
import com.example.readerapp.domain.pages.source.PagesRepository
import com.example.readerapp.data.pages.PagesRepositoryImpl
import com.example.readerapp.data.pages.source.local.PagesLocalDataSource
import com.example.readerapp.data.pages.source.local.PagesLocalDataSourceImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val pagesDataModule = module {

    factoryOf(::PagesLocalDataSourceImpl) {
        bind<PagesLocalDataSource>()
    }

    factoryOf(::PagesRemoteDataSourceImpl) {
        bind<PagesRemoteDataSource>()
    }

    singleOf(::PagesRepositoryImpl) {
        bind<PagesRepository>()
    }
}