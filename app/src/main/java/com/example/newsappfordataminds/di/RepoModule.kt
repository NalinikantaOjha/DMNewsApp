package com.example.newsappfordataminds.di

import com.example.newsappfordataminds.data.repository.NewsRepository
import com.example.newsappfordataminds.data.repository.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
internal object RepoModule {
    @Provides
    @Singleton
    fun providesNewsRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository {
        return newsRepositoryImpl
    }
}