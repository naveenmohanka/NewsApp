package com.loc.newsapp.presentation.home

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.loc.newsapp.domain.model.Article
import com.loc.newsapp.presentation.common.ArticleCard
import com.loc.newsapp.presentation.common.MessageState

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onArticleClick: (Article) -> Unit,
    onSearchClick: () -> Unit,
    onBookmarksClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "News App",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                OutlinedButton(onClick = onSearchClick) {
                    Text("Search")
                }
                OutlinedButton(
                    onClick = onBookmarksClick,
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text("Saved")
                }
            }
        }
    ) { padding ->
        when {
            state.isLoading -> MessageState(
                text = "Loading latest news...",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            )

            state.error != null -> LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Text(
                        text = state.error.orEmpty(),
                        color = MaterialTheme.colorScheme.error
                    )
                    Button(
                        onClick = viewModel::loadNews,
                        modifier = Modifier.padding(top = 12.dp)
                    ) {
                        Text("Retry")
                    }
                }
            }

            state.articles.isEmpty() -> MessageState(
                text = "No articles found",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            )

            else -> LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.articles, key = { it.url }) { article ->
                    ArticleCard(
                        article = article,
                        onClick = { onArticleClick(article) }
                    )
                }
            }
        }
    }
}

