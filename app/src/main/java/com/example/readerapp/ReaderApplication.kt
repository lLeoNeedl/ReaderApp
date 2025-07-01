package com.example.readerapp

import android.app.Application
import com.example.readerapp.di.initKoin

class ReaderApplication : Application() {

    override fun onCreate() {

        super.onCreate()

        initKoin(this)
    }
}