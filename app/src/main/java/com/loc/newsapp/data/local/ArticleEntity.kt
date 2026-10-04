package com.loc.newsapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.loc.newsapp.domain.model.Article

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey val url: String,
    val title: String,
    val description: String?,
    val content: String?,
    val imageUrl: String?,
    val publishedAt: String?,
    val sourceName: String?
)

fun ArticleEntity.toArticle() = Article(
    title = title,
    description = description,
    content = content,
    url = url,
    imageUrl = imageUrl,
    publishedAt = publishedAt,
    sourceName = sourceName
)

fun Article.toEntity() = ArticleEntity(
    url = url,
    title = title,
    description = description,
    content = content,
    imageUrl = imageUrl,
    publishedAt = publishedAt,
    sourceName = sourceName
)
