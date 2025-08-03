package com.readerapp.readerapp.di.modules

import com.readerapp.readerapp.di.modules.domain.pagesUseCasesModule
import org.koin.dsl.module

val domainModule = module {

    includes(
        pagesUseCasesModule
    )
}