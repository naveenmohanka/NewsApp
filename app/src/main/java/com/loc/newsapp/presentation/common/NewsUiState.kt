package com.loc.newsapp.presentation.common

import com.loc.newsapp.domain.model.Article

data class NewsUiState(
    val isLoading: Boolean = false,
    val articles: List<Article> = emptyList(),
    val error: String? = null
)
