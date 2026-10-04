package com.loc.newsapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.loc.newsapp.domain.usecases.NewsUseCases
import com.loc.newsapp.presentation.common.NewsUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val newsUseCases: NewsUseCases
) : ViewModel() {
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _state = MutableStateFlow(NewsUiState())
    val state: StateFlow<NewsUiState> = _state.asStateFlow()

    private var searchJob: Job? = null

    fun onQueryChange(value: String) {
        _query.value = value
    }

    fun search() {
        val currentQuery = query.value.trim()
        if (currentQuery.isBlank()) {
            _state.value = NewsUiState(error = "Enter a topic to search")
            return
        }

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _state.value = NewsUiState(isLoading = true)
            newsUseCases.searchNews(currentQuery)
                .onSuccess { articles -> _state.value = NewsUiState(articles = articles) }
                .onFailure { error ->
                    _state.value = NewsUiState(error = error.message ?: "Search failed")
                }
        }
    }

    class Factory(
        private val newsUseCases: NewsUseCases
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchViewModel(newsUseCases) as T
        }
    }
}
