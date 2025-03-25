package com.example.newsappfordataminds.data.repository

import com.example.newsappfordataminds.data.model.NewsResponse
import com.example.newsappfordataminds.data.remote.NewsApiService
import javax.inject.Inject

internal class NewsRepositoryImpl @Inject constructor(private val newsApiService: NewsApiService) :
    NewsRepository {

    override suspend fun getRecentNews(
        query: String, from: String, apiKey: String, sortBy: String
    ): NewsResponse {
        return newsApiService.getRecentNews(query, from, apiKey, sortBy)
    }

}