package com.example.readerapp.domain.pages.usecases

import com.example.readerapp.domain.pages.model.PageContent
import com.example.readerapp.domain.pages.source.PagesRepository
import kotlinx.coroutines.flow.Flow

class GetPageContentImageUseCase(
    private val pagesRepository: PagesRepository
) {

    operator fun invoke(id: String): Flow<PageContent> {

        return pagesRepository.getPageContentImage(id = id)
    }
}