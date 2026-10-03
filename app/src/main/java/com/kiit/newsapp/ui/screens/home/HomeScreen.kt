package com.kiit.newsapp.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kiit.newsapp.model.NewsArticle
import com.kiit.newsapp.ui.components.NewsCard

@Composable
fun HomeScreen() {

    val article = NewsArticle(
        title = "India launches new satellite",
        description = "India successfully launches a new satellite into space.",
        imageUrl = null,
        source = "News Today",
        publishedAt = "2 hours ago",
        url = "https://example.com"
    )

    Column {

        Text("Latest News")

        NewsCard(article = article)

    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
