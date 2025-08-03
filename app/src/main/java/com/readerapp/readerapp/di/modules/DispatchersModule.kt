package com.readerapp.readerapp.di.modules

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

enum class AppDispatchers {
    Default,
    IO,
}

val dispatchersModule = module {

    factory(qualifier = named(AppDispatchers.Default)) {
        Dispatchers.Default
    }
        .bind<CoroutineDispatcher>()

    factory(qualifier = named(AppDispatchers.IO)) {
        Dispatchers.IO
    }
        .bind<CoroutineDispatcher>()
}