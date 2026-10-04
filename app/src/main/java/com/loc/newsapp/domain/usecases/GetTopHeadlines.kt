package com.loc.newsapp.domain.usecases

import com.loc.newsapp.domain.model.Article
import com.loc.newsapp.domain.repository.NewsRepository

class GetTopHeadlines(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(): Result<List<Article>> {
        return newsRepository.getTopHeadlines()
    }
}
