package com.example.readerapp.di.modules

import com.example.readerapp.di.modules.data.pagesDataModule
import org.koin.dsl.module

val dataModule = module {

    includes(
        pagesDataModule
    )
}