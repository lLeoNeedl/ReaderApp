package com.readerapp.readerapp.data.pages.source.local

import androidx.room.withTransaction
import com.readerapp.readerapp.data.source.local.AppDatabase
import com.readerapp.readerapp.data.source.local.pages.entity.DbPageContent
import com.readerapp.readerapp.data.source.local.pages.entity.DbPageContentImage
import com.readerapp.readerapp.data.source.local.pages.entity.mapper.DbPageContentMapper
import com.readerapp.readerapp.data.source.remote.model.pages.ApiPageContent
import com.readerapp.readerapp.data.source.remote.model.pages.mapper.ApiPageContentMapper
import com.readerapp.readerapp.domain.pages.model.PageContent
import com.readerapp.readerapp.domain.pages.model.PageContentType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class PagesLocalDataSourceImpl(
    private val database: AppDatabase,
    private val defaultDispatcher: CoroutineDispatcher
) : PagesLocalDataSource {

    private val pageContentDao = database.pageContentDao()

    override suspend fun savePageContent(content: ApiPageContent) = withContext(defaultDispatcher) {

        val flattenedContent = ApiPageContentMapper.flatten(
            apiPageContent = content,
            depth = 0,
        )

        val supportedContent = flattenedContent
            .filter { apiContent ->
                PageContentType.isSupported(typeId = apiContent.type)
            }

        val dbContentList = mutableListOf<DbPageContent>()
        val dbContentImageList = mutableListOf<DbPageContentImage>()

        var orderPosition = pageContentDao.getMaxOrderPosition() ?: 0

        supportedContent.forEach { contentFlattened ->

            val dbContent = DbPageContentMapper.toDbPageContent(
                contentFlattened = contentFlattened,
                orderPosition = orderPosition++
            )

            dbContentList.add(dbContent)

            val type = PageContentType.find(typeId = contentFlattened.type)

            if (type == PageContentType.Image) {

                val dbContentImage = DbPageContentMapper.toDbPageContentImage(
                    contentFlattened = contentFlattened
                )

                dbContentImageList.add(dbContentImage)
            }
        }

        val actualIds = dbContentList.map { dbContent ->
            dbContent.id
        }

        database.withTransaction {

            pageContentDao.deleteNotActual(actualIds = actualIds)

            pageContentDao.savePageContent(dbContentList)

            pageContentDao.savePageContentImage(dbContentImageList)
        }
    }

    override fun getPageContent(): Flow<List<PageContent>> {
        return pageContentDao.getPageContent()
            .map(DbPageContentMapper::toPageContent)
    }

    override fun getPageContentImage(id: String): Flow<PageContent> {
        return pageContentDao.getPageContentImage(id = id)
            .map(DbPageContentMapper::toPageContent)
    }
}