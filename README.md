# NewsApp

A modern Android application built with Kotlin and Jetpack Compose that delivers top headlines and allows users to search topics and bookmark articles for offline reading. The project follows Clean Architecture and MVVM design patterns with a responsive Material 3 user interface.

## Features

- **Top Headlines**: Browse real-time breaking news fetched from NewsAPI.
- **Search News**: Search articles by custom topics and keywords.
- **Article Details**: Read article details including title, description, image, and publish metadata, with an option to open the original source in an external browser.
- **Bookmark & Saved Articles**: Save favorite articles locally and remove them when no longer needed.
- **Offline Bookmark Storage**: Local persistence using Room Database to access saved articles without an active internet connection.
- **Onboarding Experience**: 3-step onboarding carousel introducing key app features to first-time users.
- **Modern Jetpack Compose UI**: Clean, declarative UI built with Material 3 components.

## Tech Stack

- **Language**: [Kotlin](https://kotlinlang.org/)
- **UI Toolkit**: [Jetpack Compose](https://developer.android.com/jetpack/compose) with Material 3
- **Architecture**: MVVM + Clean Architecture (Presentation, Domain, Data layers)
- **Networking**: [Retrofit 2](https://square.github.io/retrofit/) & Gson Converter
- **Local Persistence**: [Room Database](https://developer.android.com/training/data-storage/room)
- **Image Loading**: [Coil Compose](https://coil-kt.github.io/coil/compose/)
- **Navigation**: Navigation Compose
- **State Management**: Kotlin Coroutines, StateFlow, and Flow
- **Testing**: JUnit 4, `kotlinx-coroutines-test`, and Fake Repository pattern
- **Build System**: Gradle with Kotlin DSL and Version Catalog (`libs.versions.toml`)

## Architecture

The project follows Clean Architecture with unidirectional data flow:

```
Presentation Layer  ──>  Domain Layer  <──  Data Layer
 (UI & ViewModel)         (Use Cases)       (Repository & Data Sources)
```

- **Presentation (`presentation/`)**: Contains Jetpack Compose screens, ViewModels, navigation graph, and reusable UI components. ViewModels expose UI state using `StateFlow` and trigger domain use cases.
- **Domain (`domain/`)**: Contains business models and single-responsibility use cases (`GetTopHeadlines`, `SearchNews`, `GetSavedArticles`, `SaveArticle`, `DeleteArticle`, `IsArticleSaved`, `GetArticle`). This layer is pure Kotlin without Android framework dependencies.
- **Data (`data/`)**: Implements the repository interface. Handles network communication via Retrofit and local persistence via Room DAO and entity mappers.

## Project Structure

```text
app/src/main/java/com/loc/newsapp/
├── MainActivity.kt
├── NewsApplication.kt
├── data/
│   ├── local/
│   │   ├── ArticleDao.kt
│   │   ├── ArticleEntity.kt
│   │   └── NewsDatabase.kt
│   ├── remote/
│   │   ├── NewsApi.kt
│   │   ├── NewsDto.kt
│   │   ├── NewsDtoMapper.kt
│   │   └── RetrofitInstance.kt
│   └── repository/
│       └── NewsRepository.kt
├── domain/
│   ├── model/
│   │   └── Article.kt
│   ├── repository/
│   │   └── NewsRepository.kt
│   └── usecases/
│       ├── DeleteArticle.kt
│       ├── GetArticle.kt
│       ├── GetSavedArticles.kt
│       ├── GetTopHeadlines.kt
│       ├── IsArticleSaved.kt
│       ├── NewsUseCases.kt
│       ├── SaveArticle.kt
│       └── SearchNews.kt
├── presentation/
│   ├── bookmark/
│   │   ├── BookmarkScreen.kt
│   │   └── BookmarkViewModel.kt
│   ├── common/
│   │   ├── ArticleCard.kt
│   │   ├── NewsButton.kt
│   │   └── NewsUiState.kt
│   ├── details/
│   │   └── DetailsScreen.kt
│   ├── home/
│   │   ├── HomeScreen.kt
│   │   └── HomeViewModel.kt
│   ├── nav/
│   │   ├── NewsNavGraph.kt
│   │   └── Routes.kt
│   ├── onboarding/
│   │   ├── Dimens.kt
│   │   ├── OnBoardingScreen.kt
│   │   ├── Page.kt
│   │   └── components/
│   │       ├── OnBoardingPage.kt
│   │       └── PageIndicator.kt
│   └── search/
│       ├── SearchScreen.kt
│       └── SearchViewModel.kt
└── ui/
    └── theme/
        ├── Color.kt
        ├── Theme.kt
        └── Type.kt
```

## Setup & Installation

### Prerequisites
- Android Studio Ladybug (or newer)
- Android SDK 36 / 37
- JDK 11 or higher
- A free API key from [NewsAPI.org](https://newsapi.org/)

### Getting Started

1. **Clone the repository**:
```bash
git clone https://github.com/naveenmohanka/NewsApp.git
cd NewsApp
```

2. **Open the project** in Android Studio.

3. **Configure the NewsAPI Key**:
Add your API key to `local.properties` in the root directory (create the file if it does not exist):
```properties
NEWS_API_KEY=your_news_api_key_here
```
> **Note**: `local.properties` is ignored by Git and should never be committed.

4. **Sync Gradle**:
Click **Sync Project with Gradle Files** in Android Studio or run:
```bash
./gradlew build
```

5. **Run the App**:
Select an emulator or connected Android device and click **Run** (`Shift + F10`).

## Testing

Unit tests for ViewModels are written using JUnit 4 and `kotlinx-coroutines-test`, isolated with a fake repository (`FakeNewsRepository`).

To run unit tests:

```bash
# On Linux / macOS
./gradlew testDebugUnitTest

# On Windows
.\gradlew.bat testDebugUnitTest
```

## Build

To compile and build the debug APK:

```bash
# On Linux / macOS
./gradlew assembleDebug

# On Windows
.\gradlew.bat :app:assembleDebug
```

The APK is generated at:
```text
app/build/outputs/apk/debug/app-debug.apk
```

## Screenshots

| Onboarding | Home |
|---|---|
| *Add screenshot* | *Add screenshot* |

| Search | Details |
|---|---|
| *Add screenshot* | *Add screenshot* |

| Bookmarks |
|---|
| *Add screenshot* |

## Git Workflow

Development takes place on feature branches (such as `feature/api-integration`) before merging into the main branch.

## Future Improvements

- [ ] Add pagination / infinite scrolling using Paging 3
- [ ] Add category filter tabs (Technology, Business, Sports, Entertainment)
- [ ] Implement full offline caching for the headlines feed
- [ ] Integrate Dependency Injection with Dagger Hilt
- [ ] Expand unit and UI test coverage

## License

This project is developed for educational purposes.
