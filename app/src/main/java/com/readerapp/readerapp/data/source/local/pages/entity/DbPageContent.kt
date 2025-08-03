package com.readerapp.readerapp.data.source.local.pages.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "page_content")
data class DbPageContent(
    @PrimaryKey val id: String,
    val type: String,
    val text: String,
    val depth: Int,
    val orderPosition: Int
)