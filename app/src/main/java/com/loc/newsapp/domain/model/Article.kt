package com.loc.newsapp.domain.model

import java.io.Serializable

data class Article(
    val title: String,
    val description: String?,
    val content: String?,
    val url: String,
    val imageUrl: String?,
    val publishedAt: String?,
    val sourceName: String?
) : Serializable
