package com.readerapp.readerapp.di

import com.readerapp.readerapp.ReaderApplication
import com.readerapp.readerapp.di.modules.dataModule
import com.readerapp.readerapp.di.modules.databaseModule
import com.readerapp.readerapp.di.modules.dispatchersModule
import com.readerapp.readerapp.di.modules.domainModule
import com.readerapp.readerapp.di.modules.remoteModule
import com.readerapp.readerapp.di.modules.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

fun initKoin(application: ReaderApplication) {

    startKoin {

        androidContext(application)

        modules(
            dataModule,
            databaseModule,
            dispatchersModule,
            domainModule,
            remoteModule,
            viewModelsModule,
        )
    }
}