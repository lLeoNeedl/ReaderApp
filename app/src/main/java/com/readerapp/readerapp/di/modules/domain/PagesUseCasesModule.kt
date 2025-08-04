package com.readerapp.readerapp.di.modules.domain

import com.readerapp.readerapp.domain.pages.usecases.GetPageContentUseCase
import com.readerapp.readerapp.domain.pages.usecases.GetPageContentImageUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val pagesUseCasesModule = module {

    factoryOf(::GetPageContentUseCase)
    factoryOf(::GetPageContentImageUseCase)
}