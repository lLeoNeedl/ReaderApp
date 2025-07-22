@file:OptIn(ExperimentalCoroutinesApi::class)
package com.example.readerapp.data.utils

import app.cash.turbine.test
import com.example.readerapp.domain.dataload.DataLoadConfig
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class DataLoadUtilsTest {

    private val localData = "LocalData"
    private val remoteData = "RemoteData"

    @Test
    fun offlineFirstEmitsLocalDataBeforeAndAfterSaveRemote() = runTest {

        val getRemoteData = flow {
            delay(100)
            emit(remoteData)
        }

        val getLocalData = MutableStateFlow(localData)

        val flow = dataLoadFlow(
            getLocalData = getLocalData,
            getRemoteData = getRemoteData,
            saveRemoteData = { remoteData ->
                getLocalData.value = remoteData
            },
            dataLoadConfig = DataLoadConfig.OfflineFirst
        )

        flow.test {
            assertEquals(localData, awaitItem())
            assertEquals(remoteData, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun localOnlyEmitsOnlyLocalData() = runTest {

        val flow = dataLoadFlow<String, Unit>(
            getLocalData = flowOf(localData),
            dataLoadConfig = DataLoadConfig.LocalOnly
        )

        flow.test {
            assertEquals(localData, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun filterLocalIfListFiltersOutEmptyListsWhenRemoteDataIsNotSaved() = runTest {

        val localData = emptyList<String>()

        val getLocalData = flowOf(localData)

        getLocalData
            .filterLocalIfList(remoteSaved = false)
            .test {
                awaitComplete()
            }
    }

    @Test
    fun filterLocalIfListDoesNotFilterOutEmptyListsWhenRemoteDataIsSaved() = runTest {

        val localData = emptyList<String>()

        val getLocalData = flowOf(localData)

        getLocalData
            .filterLocalIfList(remoteSaved = true)
            .test {
                assertEquals(localData, awaitItem())
                awaitComplete()
            }
    }
}