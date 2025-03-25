package com.example.newsappfordataminds.presentation.ui.compose

import android.net.Uri
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newsappfordataminds.presentation.model.RecentNewsUiState

internal sealed class Screen(val route: String) {
    data object RecentNews : Screen("recentNews")

    data object Detail :
        Screen("detail/{title}/{description}/{imageUrl}/{sourceName}/{publishedDate}/{author}") {
        fun createRoute(
            title: String?,
            description: String?,
            imageUrl: String?,
            sourceName: String?,
            publishedDate: String?,
            author: String?
        ) = "detail/${
            Uri.encode(title.orEmpty())
        }/${
            Uri.encode(description.orEmpty())
        }/${
            Uri.encode(imageUrl.orEmpty())
        }/${
            Uri.encode(sourceName.orEmpty())
        }/${
            Uri.encode(publishedDate.orEmpty())
        }/${
            Uri.encode(author.orEmpty())
        }"
    }
}

@Composable
internal fun AppNavGraph(navController: NavHostController, uiState: RecentNewsUiState) {
    NavHost(navController = navController, startDestination = Screen.RecentNews.route) {

        composable(Screen.RecentNews.route) {
            RecentNewsUi(uiState) {
                navController.navigate(
                    Screen.Detail.createRoute(
                        it.title,
                        it.description,
                        it.imageUrl,
                        it.sourceName,
                        it.publishedDate,
                        it.author
                    )
                )
            }
        }

        composable(route = Screen.Detail.route,
            arguments = listOf(navArgument("title") { type = NavType.StringType },
                navArgument("description") { type = NavType.StringType },
                navArgument("imageUrl") { type = NavType.StringType },
                navArgument("sourceName") { type = NavType.StringType },
                navArgument("publishedDate") { type = NavType.StringType },
                navArgument("author") { type = NavType.StringType }),
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(500)) + fadeIn(
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 1000 }, animationSpec = tween(500)
                ) + fadeOut(animationSpec = tween(500))
            }) { backStack ->
            val args = backStack.arguments
            NewsDetailScreen(
                title = Uri.decode(args?.getString("title").orEmpty()),
                description = Uri.decode(args?.getString("description").orEmpty()),
                imageUrl = Uri.decode(args?.getString("imageUrl").orEmpty()),
                sourceName = Uri.decode(args?.getString("sourceName").orEmpty()),
                publishedDate = Uri.decode(args?.getString("publishedDate").orEmpty()),
                author = Uri.decode(args?.getString("author").orEmpty())
            )
        }
    }
}
