package com.readerapp.readerapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.readerapp.readerapp.data.source.local.pages.entity.DbPageContent
import com.readerapp.readerapp.data.source.local.pages.PageContentDao
import com.readerapp.readerapp.data.source.local.pages.entity.DbPageContentImage

@Database(
    entities = [DbPageContent::class, DbPageContentImage::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pageContentDao(): PageContentDao

    companion object {
        const val DATABASE_NAME = "app_db"
    }
}