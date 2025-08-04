package com.readerapp.readerapp.di.modules

import com.readerapp.readerapp.ui.pages.details.ImageDetailsViewModel
import com.readerapp.readerapp.ui.pages.list.PagesViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelsModule = module {
    viewModelOf(::PagesViewModel)
    viewModelOf(::ImageDetailsViewModel)
}