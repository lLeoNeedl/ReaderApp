package com.readerapp.readerapp

import android.app.Application
import com.readerapp.readerapp.di.initKoin

class ReaderApplication : Application() {

    override fun onCreate() {

        super.onCreate()

        initKoin(this)
    }
}