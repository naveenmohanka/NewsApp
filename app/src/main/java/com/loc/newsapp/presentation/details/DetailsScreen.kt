package com.loc.newsapp.presentation.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.loc.newsapp.domain.model.Article
import com.loc.newsapp.domain.usecases.NewsUseCases
import com.loc.newsapp.presentation.common.MessageState
import kotlinx.coroutines.launch

@Composable
fun DetailsScreen(
    article: Article?,
    newsUseCases: NewsUseCases,
    onBackClick: () -> Unit
) {
    if (article == null) {
        MessageState(text = "Article not found", modifier = Modifier.fillMaxSize())
        return
    }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val isSaved by newsUseCases.isArticleSaved(article.url).collectAsState(initial = false)

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = onBackClick) {
                    Text("Back")
                }
                Button(
                    onClick = {
                        scope.launch {
                            if (isSaved) newsUseCases.deleteArticle(article) else newsUseCases.saveArticle(article)
                        }
                    }
                ) {
                    Text(if (isSaved) "Remove" else "Bookmark")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            article.imageUrl?.let {
                AsyncImage(
                    model = it,
                    contentDescription = article.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            Text(
                text = article.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = listOfNotNull(article.sourceName, article.publishedAt?.take(10)).joinToString(" • "),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = article.description ?: article.content ?: "No description available.",
                style = MaterialTheme.typography.bodyLarge
            )
            article.content?.takeIf { it != article.description }?.let {
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = it, style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(article.url)))
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Open Original Article")
            }
        }
    }
}
