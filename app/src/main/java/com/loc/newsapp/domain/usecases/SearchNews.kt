package com.loc.newsapp.domain.usecases

import com.loc.newsapp.domain.model.Article
import com.loc.newsapp.domain.repository.NewsRepository

class SearchNews(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(query: String): Result<List<Article>> {
        return newsRepository.searchNews(query)
    }
}
