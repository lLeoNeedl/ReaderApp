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
            httpClient.get(Endpoint.PAGE)
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
        const val PAGE = "v1/f118b9f0-6f84-435e-85d5-faf4453eb72a"
    }

    companion object {
        const val BASE_URL = "https://mocki.io/"
    }
}