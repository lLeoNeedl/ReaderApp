package com.example.readerapp.data.source.local.pages.entity

import androidx.room.ColumnInfo

data class DbPageContentFull(
    val id: String,
    val type: String,
    val text: String,
    val depth: Int,
    @ColumnInfo("url") val imageUrl: String?,
)