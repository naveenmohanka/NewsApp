package com.kiit.newsapp.model

data class NewsArticle(
    val title: String,
    val description: String?,
    val imageUrl: String?,
    val source: String?,
    val publishedAt: String,
    val url: String
)