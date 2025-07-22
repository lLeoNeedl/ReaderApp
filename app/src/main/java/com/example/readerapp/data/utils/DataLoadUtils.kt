package com.example.readerapp.data.utils

import androidx.annotation.VisibleForTesting
import com.example.readerapp.domain.dataload.DataLoadConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

private const val LOCAL_EMIT_DELAY = 1000L

fun <Local, Remote> dataLoadFlow(
    getLocalData: Flow<Local>,
    getRemoteData: Flow<Remote>? = null,
    saveRemoteData: (suspend (Remote) -> Unit)? = null,
    dataLoadConfig: DataLoadConfig = DataLoadConfig.OfflineFirst
): Flow<Local> {

    return when (dataLoadConfig) {

        is DataLoadConfig.OfflineFirst -> {

            requireNotNull(getRemoteData)
            requireNotNull(saveRemoteData)

            var localEmitted = false
            var remoteSaved = false

            channelFlow {

                launch {

                    getAndSaveRemote(
                        getRemoteData = getRemoteData,
                        saveRemoteData = saveRemoteData
                    )
                        .onEach {
                            remoteSaved = true
                        }
                        .catch { throwable ->

                            // Gives local data a chance to emit before throwing an error
                            delay(LOCAL_EMIT_DELAY)

                            if (!localEmitted) {
                                close(throwable)
                            }
                        }
                        .collect()
                }

                getLocalData
                    // Filters out empty lists from local data before remote data is saved,
                    // allowing a loading state to be shown instead of an empty state while remote data is loading.
                    .filterLocalIfList(
                        remoteSaved = remoteSaved
                    )
                    .onEach { local ->
                        localEmitted = true
                        send(local)
                    }
                    .collect()
            }
        }

        is DataLoadConfig.LocalOnly -> {

            require(getRemoteData == null && saveRemoteData == null) {
                "LocalOnly must not have any remote and save operations"
            }

            getLocalData
        }
    }
        .distinctUntilChanged()
}

private fun <Remote> getAndSaveRemote(
    getRemoteData: Flow<Remote>,
    saveRemoteData: suspend (Remote) -> Unit,
): Flow<Unit> {
    return getRemoteData
        .onEach { remoteData ->
            saveRemoteData(remoteData)
        }
        .map {}
}

@VisibleForTesting
internal fun <Local> Flow<Local>.filterLocalIfList(
    remoteSaved: Boolean
): Flow<Local> {

    return filterNot { local ->
        (local as? List<*>)?.isEmpty() == true && !remoteSaved
    }
}