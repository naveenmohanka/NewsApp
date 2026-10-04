package com.loc.newsapp.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.loc.newsapp.domain.model.Article
import com.loc.newsapp.domain.usecases.NewsUseCases
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BookmarkViewModel(
    private val newsUseCases: NewsUseCases
) : ViewModel() {
    val articles: StateFlow<List<Article>> = newsUseCases.getSavedArticles()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun deleteArticle(article: Article) {
        viewModelScope.launch {
            newsUseCases.deleteArticle(article)
        }
    }

    class Factory(
        private val newsUseCases: NewsUseCases
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return BookmarkViewModel(newsUseCases) as T
        }
    }
}
