package com.example.usecase

import com.example.newsappfordataminds.data.model.NewsResponse
import com.example.newsappfordataminds.data.repository.NewsRepository
import com.example.newsappfordataminds.domain.usecase.RecentNewsUseCase
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RecentNewsUseCaseTest {

    private lateinit var newsRepository: NewsRepository
    private lateinit var recentNewsUseCase: RecentNewsUseCase

    @Before
    fun setUp() {
        newsRepository = mockk()
        recentNewsUseCase = RecentNewsUseCase(newsRepository)
    }

    @Test
    fun `invoke should return news response from repository`() = runBlocking {
        val query = "technology"
        val from = "2024-03-17"
        val apiKey = "test_api_key"
        val sortBy = "publishedAt"
        val expectedResponse = mockk<NewsResponse>()

        coEvery {
            newsRepository.getRecentNews(
                query, from, apiKey, sortBy
            )
        } returns expectedResponse

        val result = recentNewsUseCase(query, from, apiKey, sortBy)

        assertEquals(expectedResponse, result)
    }
}
