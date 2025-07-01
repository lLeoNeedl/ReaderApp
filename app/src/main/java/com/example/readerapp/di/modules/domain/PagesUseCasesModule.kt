package com.example.readerapp.di.modules.domain

import com.example.readerapp.domain.pages.usecases.GetPageContentUseCase
import com.example.readerapp.domain.pages.usecases.GetPageContentImageUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val pagesUseCasesModule = module {

    factoryOf(::GetPageContentUseCase)
    factoryOf(::GetPageContentImageUseCase)
}