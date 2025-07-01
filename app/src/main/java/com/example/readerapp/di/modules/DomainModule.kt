package com.example.readerapp.di.modules

import com.example.readerapp.di.modules.domain.pagesUseCasesModule
import org.koin.dsl.module

val domainModule = module {

    includes(
        pagesUseCasesModule
    )
}