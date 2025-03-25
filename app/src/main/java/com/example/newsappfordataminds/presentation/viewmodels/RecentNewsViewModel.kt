package com.example.newsappfordataminds.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsappfordataminds.domain.usecase.RecentNewsUseCase
import com.example.newsappfordataminds.presentation.model.RecentNewsUiModel
import com.example.newsappfordataminds.presentation.model.RecentNewsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class RecentNewsViewModel @Inject constructor(private val recentNewsUseCase: RecentNewsUseCase) :
    ViewModel() {
    private val _newsState = MutableStateFlow<RecentNewsUiState>(RecentNewsUiState.Loading)
    val newsState = _newsState.asStateFlow()
     fun getRecentNews(
        query: String = "tesla",
        fromDate: String = "2025-02-24",
        apiKey: String = "325fe0100ad94b8bbe939a7ba7e08916",
        sortBy: String = "publishedAt"
    ) {

        viewModelScope.launch {
            try {
                val response = recentNewsUseCase(
                    query, fromDate, apiKey, sortBy
                )
                _newsState.update{ RecentNewsUiState . Success (response.articles.orEmpty().map {
                    RecentNewsUiModel(
                        it.title, it.description, it.urlToImage, it.source?.name, it.publishedAt,it.author
                    )
                })
            }
            } catch (e: Exception) {
                _newsState.update {
                    RecentNewsUiState.Error(e.message.toString())
                }
            }
        }
    }
}
