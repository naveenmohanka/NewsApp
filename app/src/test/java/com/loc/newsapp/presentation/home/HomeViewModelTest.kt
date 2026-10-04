package com.loc.newsapp.presentation.home

import com.loc.newsapp.data.repository.FakeNewsRepository
import com.loc.newsapp.domain.model.Article
import com.loc.newsapp.domain.usecases.DeleteArticle
import com.loc.newsapp.domain.usecases.GetArticle
import com.loc.newsapp.domain.usecases.GetSavedArticles
import com.loc.newsapp.domain.usecases.GetTopHeadlines
import com.loc.newsapp.domain.usecases.NewsUseCases
import com.loc.newsapp.domain.usecases.SaveArticle
import com.loc.newsapp.domain.usecases.SearchNews
import com.loc.newsapp.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var fakeRepository: FakeNewsRepository
    private lateinit var newsUseCases: NewsUseCases

    @Before
    fun setUp() {
        fakeRepository = FakeNewsRepository()
        newsUseCases = NewsUseCases(
            getTopHeadlines = GetTopHeadlines(fakeRepository),
            searchNews = SearchNews(fakeRepository),
            getArticle = GetArticle(fakeRepository),
            getSavedArticles = GetSavedArticles(fakeRepository),
            saveArticle = SaveArticle(fakeRepository),
            deleteArticle = DeleteArticle(fakeRepository)
        )
    }

    @Test
    fun loadNews_success_updatesStateWithArticlesAndStopsLoading() = runTest {
        val sampleArticles = listOf(
            Article(
                title = "Test Article 1",
                description = "Description 1",
                content = "Content 1",
                url = "https://example.com/1",
                imageUrl = "https://example.com/1.jpg",
                publishedAt = "2026-10-04T12:00:00Z",
                sourceName = "BBC"
            ),
            Article(
                title = "Test Article 2",
                description = "Description 2",
                content = "Content 2",
                url = "https://example.com/2",
                imageUrl = "https://example.com/2.jpg",
                publishedAt = "2026-10-04T13:00:00Z",
                sourceName = "CNN"
            )
        )
        fakeRepository.articles.addAll(sampleArticles)

        val viewModel = HomeViewModel(newsUseCases)

        val currentState = viewModel.state.value
        assertFalse(currentState.isLoading)
        assertNull(currentState.error)
        assertEquals(sampleArticles, currentState.articles)
    }

    @Test
    fun loadNews_failure_updatesStateWithErrorAndStopsLoading() = runTest {
        fakeRepository.shouldReturnError = true

        val viewModel = HomeViewModel(newsUseCases)

        val currentState = viewModel.state.value
        assertFalse(currentState.isLoading)
        assertEquals("Network error", currentState.error)
        assertTrue(currentState.articles.isEmpty())
    }
}
