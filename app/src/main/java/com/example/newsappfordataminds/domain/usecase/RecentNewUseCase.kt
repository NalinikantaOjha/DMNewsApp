package com.example.newsappfordataminds.domain.usecase


import com.example.newsappfordataminds.data.model.NewsResponse
import com.example.newsappfordataminds.data.repository.NewsRepository
import javax.inject.Inject

internal class RecentNewsUseCase @Inject constructor(private val repository: NewsRepository) {
    suspend operator fun invoke(query: String,from:String,apiKey:String,sortBy:String): NewsResponse {
    return  repository.getRecentNews(  query, from, apiKey, sortBy)
    }
}
