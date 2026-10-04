package com.loc.newsapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Query("SELECT * FROM articles ORDER BY publishedAt DESC")
    fun getArticles(): Flow<List<ArticleEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM articles WHERE url = :url)")
    fun isArticleSaved(url: String): Flow<Boolean>

    @Upsert
    suspend fun upsert(article: ArticleEntity)

    @Delete
    suspend fun delete(article: ArticleEntity)
}
