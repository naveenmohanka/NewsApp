package com.loc.newsapp.data.repository

import com.loc.newsapp.data.local.ArticleDao
import com.loc.newsapp.data.local.toArticle
import com.loc.newsapp.data.local.toEntity
import com.loc.newsapp.data.remote.NewsApi
import com.loc.newsapp.data.remote.toArticle
import com.loc.newsapp.domain.model.Article
import com.loc.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsRepositoryImpl(
    private val newsApi: NewsApi,
    private val articleDao: ArticleDao,
    private val apiKey: String
) : NewsRepository {

    override suspend fun getTopHeadlines(): Result<List<Article>> {
        return runApiCall {
            newsApi.getTopHeadlines(apiKey = apiKey).articles.orEmpty().mapNotNull { it.toArticle() }
        }
    }

    override suspend fun searchNews(query: String): Result<List<Article>> {
        return runApiCall {
            newsApi.searchNews(query = query, apiKey = apiKey).articles.orEmpty().mapNotNull { it.toArticle() }
        }
    }

    override fun getSavedArticles(): Flow<List<Article>> {
        return articleDao.getArticles().map { articles -> articles.map { it.toArticle() } }
    }

    override suspend fun saveArticle(article: Article) {
        articleDao.upsert(article.toEntity())
    }

    override suspend fun deleteArticle(article: Article) {
        articleDao.delete(article.toEntity())
    }

    override suspend fun getArticle(url: String): Article? {
        return articleDao.getArticle(url)?.toArticle()
    }

    override fun isArticleSaved(url: String): Flow<Boolean> {
        return articleDao.isArticleSaved(url)
    }

    private suspend fun runApiCall(block: suspend () -> List<Article>): Result<List<Article>> {
        if (apiKey.isBlank()) {
            return Result.failure(IllegalStateException("Missing NEWS_API_KEY in local.properties"))
        }
        return try {
            Result.success(block())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
