package com.loc.newsapp.domain.usecases

data class NewsUseCases(
    val getTopHeadlines: GetTopHeadlines,
    val searchNews: SearchNews,
    val getArticle: GetArticle,
    val getSavedArticles: GetSavedArticles,
    val saveArticle: SaveArticle,
    val deleteArticle: DeleteArticle
)
