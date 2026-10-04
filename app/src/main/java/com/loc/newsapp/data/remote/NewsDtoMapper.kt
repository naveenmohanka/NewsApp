package com.loc.newsapp.data.remote

import com.loc.newsapp.domain.model.Article

fun ArticleDto.toArticle(): Article? {
    val articleUrl = url?.takeIf { it.isNotBlank() } ?: return null
    val articleTitle = title?.takeIf { it.isNotBlank() && it != "[Removed]" } ?: return null
    return Article(
        title = articleTitle,
        description = description,
        content = content,
        url = articleUrl,
        imageUrl = urlToImage,
        publishedAt = publishedAt,
        sourceName = source?.name
    )
}
