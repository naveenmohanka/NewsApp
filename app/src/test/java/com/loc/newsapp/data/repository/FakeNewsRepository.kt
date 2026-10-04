package com.loc.newsapp.data.repository

import com.loc.newsapp.domain.model.Article
import com.loc.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeNewsRepository : NewsRepository {
    var shouldReturnError: Boolean = false
    val articles = mutableListOf<Article>()
    val savedArticles = mutableListOf<Article>()

    override suspend fun getTopHeadlines(): Result<List<Article>> {
        return if (shouldReturnError) {
            Result.failure(RuntimeException("Network error"))
        } else {
            Result.success(articles)
        }
    }

    override suspend fun searchNews(query: String): Result<List<Article>> {
        return if (shouldReturnError) {
            Result.failure(RuntimeException("Search error"))
        } else {
            Result.success(articles.filter { it.title.contains(query, ignoreCase = true) })
        }
    }

    override fun getSavedArticles(): Flow<List<Article>> {
        return flowOf(savedArticles)
    }

    override suspend fun saveArticle(article: Article) {
        savedArticles.add(article)
    }

    override suspend fun deleteArticle(article: Article) {
        savedArticles.remove(article)
    }

    override suspend fun getArticle(url: String): Article? {
        return savedArticles.find { it.url == url }
    }

    override fun isArticleSaved(url: String): Flow<Boolean> {
        return flowOf(savedArticles.any { it.url == url })
    }
}
