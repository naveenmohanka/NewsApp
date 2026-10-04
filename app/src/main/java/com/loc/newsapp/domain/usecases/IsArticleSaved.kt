package com.loc.newsapp.domain.usecases

import com.loc.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class IsArticleSaved(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(url: String): Flow<Boolean> {
        return newsRepository.isArticleSaved(url)
    }
}
