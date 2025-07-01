package com.example.readerapp.di.modules

import androidx.room.Room
import com.example.readerapp.data.source.local.AppDatabase
import org.koin.dsl.module

val databaseModule = module {

    single {

        Room.databaseBuilder(
            context = get(),
            klass = AppDatabase::class.java,
            name = AppDatabase.DATABASE_NAME
        )
            .build()
    }
}