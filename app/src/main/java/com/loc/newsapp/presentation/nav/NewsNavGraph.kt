package com.loc.newsapp.presentation.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.loc.newsapp.domain.model.Article
import com.loc.newsapp.domain.usecases.NewsUseCases
import com.loc.newsapp.presentation.bookmark.BookmarkScreen
import com.loc.newsapp.presentation.bookmark.BookmarkViewModel
import com.loc.newsapp.presentation.details.DetailsScreen
import com.loc.newsapp.presentation.home.HomeScreen
import com.loc.newsapp.presentation.home.HomeViewModel
import com.loc.newsapp.presentation.onboarding.OnBoardingScreen
import com.loc.newsapp.presentation.search.SearchScreen
import com.loc.newsapp.presentation.search.SearchViewModel

@Composable
fun NewsNavGraph(newsUseCases: NewsUseCases) {
    val navController = rememberNavController()
    var selectedArticle by remember { mutableStateOf<Article?>(null) }

    NavHost(
        navController = navController,
        startDestination = Routes.Onboarding
    ) {
        composable(Routes.Onboarding) {
            OnBoardingScreen(
                onGetStarted = {
                    navController.navigate(Routes.Home) {
                        popUpTo(Routes.Onboarding) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.Home) {
            val viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory(newsUseCases))
            HomeScreen(
                viewModel = viewModel,
                onArticleClick = {
                    selectedArticle = it
                    navController.navigate(Routes.Details)
                },
                onSearchClick = { navController.navigate(Routes.Search) },
                onBookmarksClick = { navController.navigate(Routes.Bookmarks) }
            )
        }
        composable(Routes.Search) {
            val viewModel: SearchViewModel = viewModel(factory = SearchViewModel.Factory(newsUseCases))
            SearchScreen(
                viewModel = viewModel,
                onArticleClick = {
                    selectedArticle = it
                    navController.navigate(Routes.Details)
                },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Routes.Bookmarks) {
            val viewModel: BookmarkViewModel = viewModel(factory = BookmarkViewModel.Factory(newsUseCases))
            BookmarkScreen(
                viewModel = viewModel,
                onArticleClick = {
                    selectedArticle = it
                    navController.navigate(Routes.Details)
                },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Routes.Details) {
            DetailsScreen(
                article = selectedArticle,
                newsUseCases = newsUseCases,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
