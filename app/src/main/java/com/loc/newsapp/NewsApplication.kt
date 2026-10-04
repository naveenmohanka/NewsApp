package com.loc.newsapp

import android.app.Application
import com.loc.newsapp.data.local.NewsDatabase
import com.loc.newsapp.data.remote.RetrofitInstance
import com.loc.newsapp.data.repository.NewsRepositoryImpl
import com.loc.newsapp.domain.usecases.DeleteArticle
import com.loc.newsapp.domain.usecases.GetArticle
import com.loc.newsapp.domain.usecases.GetSavedArticles
import com.loc.newsapp.domain.usecases.GetTopHeadlines
import com.loc.newsapp.domain.usecases.NewsUseCases
import com.loc.newsapp.domain.usecases.SaveArticle
import com.loc.newsapp.domain.usecases.SearchNews

class NewsApplication : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}

class AppContainer(application: Application) {
    private val database = NewsDatabase.create(application)
    private val repository = NewsRepositoryImpl(
        newsApi = RetrofitInstance.api,
        articleDao = database.articleDao(),
        apiKey = BuildConfig.NEWS_API_KEY
    )

    val newsUseCases = NewsUseCases(
        getTopHeadlines = GetTopHeadlines(repository),
        searchNews = SearchNews(repository),
        getArticle = GetArticle(repository),
        getSavedArticles = GetSavedArticles(repository),
        saveArticle = SaveArticle(repository),
        deleteArticle = DeleteArticle(repository)
    )
}
