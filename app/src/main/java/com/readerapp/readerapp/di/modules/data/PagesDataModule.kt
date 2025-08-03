package com.readerapp.readerapp.di.modules.data

import com.readerapp.readerapp.data.pages.source.remote.PagesRemoteDataSource
import com.readerapp.readerapp.data.pages.source.remote.PagesRemoteDataSourceImpl
import com.readerapp.readerapp.domain.pages.source.PagesRepository
import com.readerapp.readerapp.data.pages.PagesRepositoryImpl
import com.readerapp.readerapp.data.pages.source.local.PagesLocalDataSource
import com.readerapp.readerapp.data.pages.source.local.PagesLocalDataSourceImpl
import com.readerapp.readerapp.di.modules.AppDispatchers
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val pagesDataModule = module {

    factory {
        PagesLocalDataSourceImpl(
            database = get(),
            defaultDispatcher = get(qualifier = named(AppDispatchers.Default))
        )
    }
        .bind<PagesLocalDataSource>()

    factoryOf(::PagesRemoteDataSourceImpl) {
        bind<PagesRemoteDataSource>()
    }

    singleOf(::PagesRepositoryImpl) {
        bind<PagesRepository>()
    }
}