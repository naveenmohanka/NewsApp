package com.loc.newsapp

import android.app.Application
import com.loc.newsapp.data.local.NewsDatabase
import com.loc.newsapp.data.remote.RetrofitInstance
import com.loc.newsapp.data.repository.NewsRepositoryImpl
import com.loc.newsapp.domain.usecases.NewsUseCases

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

    val newsUseCases = NewsUseCases(repository)
}
