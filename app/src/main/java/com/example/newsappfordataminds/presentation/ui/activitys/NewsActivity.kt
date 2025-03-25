package com.example.newsappfordataminds.presentation.ui.activitys

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.rememberNavController
import com.example.newsappfordataminds.presentation.ui.compose.AppNavGraph
import com.example.newsappfordataminds.presentation.ui.compose.theme.NewsAppForDataMindsTheme
import com.example.newsappfordataminds.presentation.viewmodels.RecentNewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val recentNewsViewModel: RecentNewsViewModel by viewModels()
        setContent {
            NewsAppForDataMindsTheme {
                LaunchedEffect(Unit) {
                    recentNewsViewModel.getRecentNews()
                }
                recentNewsViewModel.newsState.collectAsState().value.let {
                    val navController = rememberNavController()

                    AppNavGraph(navController,
                        recentNewsViewModel.newsState.value,
                    )
                }
            }
        }
    }
}