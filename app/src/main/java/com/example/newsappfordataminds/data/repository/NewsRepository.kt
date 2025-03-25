package com.example.newsappfordataminds.data.repository

import com.example.newsappfordataminds.data.model.NewsResponse

internal fun interface NewsRepository {
    suspend fun getRecentNews(query: String, from: String, apiKey: String, sortBy: String):NewsResponse
}