package com.example.newsappfordataminds.data.remote

import com.example.newsappfordataminds.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal fun interface NewsApiService {
    @GET("/v2/everything")
    suspend fun getRecentNews(
        @Query("q") query: String,
        @Query("from") from: String,
        @Query("apiKey") apiKey: String,
        @Query("sortBy") sortBy: String
    ): NewsResponse
}
