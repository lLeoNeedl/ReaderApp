package com.example.readerapp.di.modules

import androidx.room.Room
import com.example.readerapp.data.source.local.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    single {

        Room.databaseBuilder(
            context = androidApplication(),
            klass = AppDatabase::class.java,
            name = AppDatabase.DATABASE_NAME
        )
            .build()
    }
}