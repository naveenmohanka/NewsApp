package com.loc.newsapp.data.repository

import com.loc.newsapp.domain.model.Article
import com.loc.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class FakeNewsRepository : NewsRepository {
    var shouldReturnError: Boolean = false
    val articles = mutableListOf<Article>()
    private val _savedArticles = MutableStateFlow<List<Article>>(emptyList())
    val savedArticles = _savedArticles.asStateFlow()

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
        return _savedArticles.asStateFlow()
    }

    override suspend fun saveArticle(article: Article) {
        val currentList = _savedArticles.value.toMutableList()
        val index = currentList.indexOfFirst { it.url == article.url }
        if (index >= 0) {
            currentList[index] = article
        } else {
            currentList.add(article)
        }
        _savedArticles.value = currentList
    }

    override suspend fun deleteArticle(article: Article) {
        _savedArticles.value = _savedArticles.value.filterNot { it.url == article.url }
    }

    override suspend fun getArticle(url: String): Article? {
        return _savedArticles.value.find { it.url == url }
    }

    override fun isArticleSaved(url: String): Flow<Boolean> {
        return _savedArticles.map { list -> list.any { it.url == url } }
    }
}
