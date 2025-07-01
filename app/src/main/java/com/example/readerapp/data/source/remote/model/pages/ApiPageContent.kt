package com.example.readerapp.data.source.remote.model.pages

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiPageContent(
    @SerialName("type") val type: String,
    @SerialName("title") val text: String,
    @SerialName("items") val items: List<ApiPageContent>? = null,
    @SerialName("src") val imageUrl: String? = null
)