package com.loc.newsapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.loc.newsapp.domain.usecases.NewsUseCases
import com.loc.newsapp.presentation.common.NewsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val newsUseCases: NewsUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(NewsUiState(isLoading = true))
    val state: StateFlow<NewsUiState> = _state.asStateFlow()

    init {
        loadNews()
    }

    fun loadNews() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            newsUseCases.getTopHeadlines()
                .onSuccess { articles -> _state.value = NewsUiState(articles = articles) }
                .onFailure { error ->
                    _state.value = NewsUiState(error = error.message ?: "Unable to load news")
                }
        }
    }

    class Factory(
        private val newsUseCases: NewsUseCases
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(newsUseCases) as T
        }
    }
}
