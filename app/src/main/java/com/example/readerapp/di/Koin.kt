package com.example.readerapp.di

import com.example.readerapp.ReaderApplication
import com.example.readerapp.di.modules.dataModule
import com.example.readerapp.di.modules.databaseModule
import com.example.readerapp.di.modules.dispatchersModule
import com.example.readerapp.di.modules.domainModule
import com.example.readerapp.di.modules.remoteModule
import com.example.readerapp.di.modules.viewModelsModule
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