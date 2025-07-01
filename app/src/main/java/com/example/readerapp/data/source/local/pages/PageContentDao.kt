package com.example.readerapp.data.source.local.pages

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.readerapp.data.source.local.pages.entity.DbPageContentImage
import com.example.readerapp.data.source.local.pages.entity.DbPageContent
import com.example.readerapp.data.source.local.pages.entity.DbPageContentFull
import kotlinx.coroutines.flow.Flow

@Dao
interface PageContentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePageContent(content: List<DbPageContent>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePageContentImage(content: List<DbPageContentImage>)

    @Query(
        "SELECT " +
            "content.id, " +
            "content.type, " +
            "content.text, " +
            "content.depth, " +
            "images.url " +
        "FROM page_content content " +
        "LEFT JOIN page_content_image images " +
            "ON content.id == images.id " +
        "ORDER BY orderPosition"
    )
    fun getPageContent(): Flow<List<DbPageContentFull>>

    @Query(
        "SELECT " +
            "content.id, " +
            "content.type, " +
            "content.text, " +
            "content.depth, " +
            "images.url " +
        "FROM page_content content " +
        "LEFT JOIN page_content_image images " +
            "ON content.id == images.id " +
        "WHERE content.id == :id " +
        "ORDER BY orderPosition"
    )
    fun getPageContentImage(id: String): Flow<DbPageContentFull>

    @Query("SELECT MAX(orderPosition) FROM page_content")
    suspend fun getMaxOrderPosition(): Int?

    @Query("DELETE FROM page_content WHERE id NOT IN (:actualIds)")
    suspend fun deleteNotActual(actualIds: List<String>)
}