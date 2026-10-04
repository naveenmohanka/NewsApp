package com.loc.newsapp.domain.repository

import com.loc.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getTopHeadlines(): Result<List<Article>>
    suspend fun searchNews(query: String): Result<List<Article>>
    fun getSavedArticles(): Flow<List<Article>>
    suspend fun saveArticle(article: Article)
    suspend fun deleteArticle(article: Article)
    fun isArticleSaved(url: String): Flow<Boolean>
}
