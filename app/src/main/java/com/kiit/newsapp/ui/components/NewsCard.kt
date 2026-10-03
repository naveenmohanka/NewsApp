package com.kiit.newsapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kiit.newsapp.model.NewsArticle

@Composable
fun NewsCard(article: NewsArticle) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = article.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(article.description ?: "")
            Text(article.source ?: "")
            Text(article.publishedAt)
        }
    }
}
