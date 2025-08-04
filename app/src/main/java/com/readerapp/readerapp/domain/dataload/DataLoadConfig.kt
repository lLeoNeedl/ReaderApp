package com.readerapp.readerapp.domain.dataload

sealed interface DataLoadConfig {

    /**
     * Loads data from remote datasource, saves it to local datasource, and returns local data with subscription.
     * If remote failed to load, existing local data will still be returned, making the local data the source of truth.
     */
    data object OfflineFirst : DataLoadConfig

    /**
     * Fetches only local data with subscription.
     */
    data object LocalOnly : DataLoadConfig
}