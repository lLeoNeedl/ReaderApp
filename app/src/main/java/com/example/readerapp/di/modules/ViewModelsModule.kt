package com.example.readerapp.di.modules

import com.example.readerapp.ui.pages.details.ImageDetailsViewModel
import com.example.readerapp.ui.pages.list.PagesViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelsModule = module {
    viewModelOf(::PagesViewModel)
    viewModelOf(::ImageDetailsViewModel)
}