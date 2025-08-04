package com.readerapp.readerapp.data.source.local.pages.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "page_content_image",
    foreignKeys = [
        ForeignKey(
            entity = DbPageContent::class,
            parentColumns = ["id"],
            childColumns = ["id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DbPageContentImage(
    @PrimaryKey val id: String,
    val url: String?
)