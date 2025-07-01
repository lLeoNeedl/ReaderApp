package com.example.readerapp.domain.pages.usecases

import com.example.readerapp.domain.pages.model.PageContent
import com.example.readerapp.domain.pages.source.PagesRepository
import kotlinx.coroutines.flow.Flow

class GetPageContentUseCase(
    private val pagesRepository: PagesRepository
) {

    operator fun invoke(): Flow<List<PageContent>> {

        return pagesRepository.getPageContent()
    }
}