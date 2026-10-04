package com.loc.newsapp.domain.usecases

import com.loc.newsapp.domain.model.Article
import com.loc.newsapp.domain.repository.NewsRepository

class NewsUseCases(private val repository: NewsRepository) {
    suspend fun getTopHeadlines() = repository.getTopHeadlines()
    suspend fun searchNews(query: String) = repository.searchNews(query)
    fun getSavedArticles() = repository.getSavedArticles()
    suspend fun saveArticle(article: Article) = repository.saveArticle(article)
    suspend fun deleteArticle(article: Article) = repository.deleteArticle(article)
    fun isArticleSaved(url: String) = repository.isArticleSaved(url)
}
