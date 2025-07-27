package com.example.readerapp.data.source.remote

import com.example.readerapp.data.source.remote.model.pages.ApiPageContent
import com.example.readerapp.domain.exceptions.DataAccessException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ApiClient(
    private val httpClient: HttpClient
) {

    fun getPageContent(): Flow<ApiPageContent> {

        return apiFlow<ApiPageContent> {
            httpClient.get(Endpoint.PAGES)
        }
    }

    /**
     * Executes request and wraps the response body in Flow.
     * @throws [DataAccessException] if the response status is not successful.
     */
    private inline fun <reified T> apiFlow(
        crossinline requestBlock: suspend () -> HttpResponse
    ): Flow<T> = flow {

        val response = requestBlock()

        response.ensureSuccessful()

        emit(response.body())
    }

    private fun HttpResponse.ensureSuccessful(): HttpResponse = apply {

        if (!status.isSuccess()) {

            val message = "Status code:${this.status.value}, description:${this.status.description}"

            throw DataAccessException.Unknown(message = message)
        }
    }

    object Endpoint {
        const val PAGES = "pages"
    }

    companion object {
        const val BASE_URL = "https://mpdf21ebb57e8e4603dd.free.beeceptor.com"
    }
}