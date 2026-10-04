package com.loc.newsapp.presentation.bookmark

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.loc.newsapp.domain.model.Article
import com.loc.newsapp.presentation.common.ArticleCard
import com.loc.newsapp.presentation.common.MessageState

@Composable
fun BookmarkScreen(
    viewModel: BookmarkViewModel,
    onArticleClick: (Article) -> Unit,
    onBackClick: () -> Unit
) {
    val articles by viewModel.articles.collectAsState()

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(16.dp)
            ) {
                Button(onClick = onBackClick) {
                    Text("Back")
                }
                Text(
                    text = "Bookmarks",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(start = 16.dp, top = 6.dp)
                )
            }
        }
    ) { padding ->
        if (articles.isEmpty()) {
            MessageState(
                text = "No saved articles yet",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(articles, key = { it.url }) { article ->
                    Column {
                        ArticleCard(article = article, onClick = { onArticleClick(article) })
                        TextButton(
                            onClick = { viewModel.deleteArticle(article) },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text("Remove bookmark")
                        }
                    }
                }
            }
        }
    }
}
