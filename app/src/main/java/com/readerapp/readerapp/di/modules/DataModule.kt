package com.readerapp.readerapp.di.modules

import com.readerapp.readerapp.di.modules.data.pagesDataModule
import org.koin.dsl.module

val dataModule = module {

    includes(
        pagesDataModule
    )
}