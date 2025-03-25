package com.example.newsappfordataminds.presentation.model

internal sealed class RecentNewsUiState {
    data object Loading : RecentNewsUiState()
    data class Success(val news: List<RecentNewsUiModel>) : RecentNewsUiState()
    data class Error(val message: String) : RecentNewsUiState()
}