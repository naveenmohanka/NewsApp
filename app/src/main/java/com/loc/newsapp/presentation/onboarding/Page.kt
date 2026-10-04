package com.loc.newsapp.presentation.onboarding

import androidx.annotation.DrawableRes
import com.loc.newsapp.R

data class Page(
    val title: String,
    val description: String,
    @param:DrawableRes val image: Int
)

val pages = listOf(
    Page(
        title = "Read trusted headlines",
        description = "Catch up with top stories from reliable sources in one simple app.",
        image = R.drawable.ic_onboarding_news
    ),
    Page(
        title = "Search what matters",
        description = "Find articles by topic, keyword, technology, sports, business, and more.",
        image = R.drawable.ic_onboarding_search
    ),
    Page(
        title = "Save for later",
        description = "Bookmark important stories and come back to them anytime.",
        image = R.drawable.ic_onboarding_bookmark
    )
)
