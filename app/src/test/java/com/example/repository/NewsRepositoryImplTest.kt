package com.example.repository

import com.example.newsappfordataminds.data.model.NewsResponse
import com.example.newsappfordataminds.data.remote.NewsApiService
import com.example.newsappfordataminds.data.repository.NewsRepository
import com.example.newsappfordataminds.data.repository.NewsRepositoryImpl
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NewsRepositoryImplTest {

    private lateinit var newsRepository: NewsRepository
    private val newsApiService: NewsApiService = mockk()

    @Before
    fun setUp() {
        newsRepository = NewsRepositoryImpl(newsApiService)
    }

    @Test
    fun `getRecentNews should return expected NewsResponse`() = runBlocking {
        val query = "Technology"
        val from = "2024-03-20"
        val apiKey = "test_api_key"
        val sortBy = "publishedAt"
        val expectedResponse = mockk<NewsResponse>()
        coEvery {
            newsApiService.getRecentNews(
                query, from, apiKey, sortBy
            )
        } returns expectedResponse
        val actualResponse = newsRepository.getRecentNews(query, from, apiKey, sortBy)

        assertEquals(expectedResponse, actualResponse)
    }
}
