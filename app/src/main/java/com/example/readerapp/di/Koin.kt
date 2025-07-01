package com.example.readerapp.di

import com.example.readerapp.ReaderApplication
import com.example.readerapp.di.modules.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

fun initKoin(application: ReaderApplication) {

    startKoin {

        androidContext(application)

        modules(
            domainModule,
        )
    }
}