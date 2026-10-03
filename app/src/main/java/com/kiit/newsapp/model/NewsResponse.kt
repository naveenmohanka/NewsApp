package com.kiit.newsapp.model

data class NewsResponse(
    val status: String,  // API request successful hui ya nahi
    val totalResults: Int, // kitni news mili
    val articles: List<NewsArticle> // actual news ki list
)