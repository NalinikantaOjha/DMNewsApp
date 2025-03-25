package com.example.newsappfordataminds.di

import com.example.newsappfordataminds.data.remote.NewsApiService
import com.example.newsappfordataminds.util.constant.UrlConstant.NEWS_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    fun provideNewsApiService(retrofit: Retrofit): NewsApiService {
        return retrofit.create(NewsApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideNewsRetroInstance(okHttpClientBuilder: OkHttpClient): Retrofit {
        return Retrofit.Builder().client(okHttpClientBuilder).baseUrl(NEWS_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun getClient(): OkHttpClient {
        val clientSetup = OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)

        return clientSetup.addInterceptor(getLoggingInterceptor())
            .addInterceptor(getAuthInterceptor()).build()

    }

    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = (HttpLoggingInterceptor.Level.BODY)
        return logging
    }

    private fun makeCustomErrorResponse(response: Response, request: Request): Response {
        return Response.Builder().request(request).protocol(response.protocol).code(response.code)
            .message("News api failed url:${request.url} body: ${response.message}")
            .body(response.body).build()
    }

    @JvmStatic
    private fun getAuthInterceptor(): Interceptor {
        return Interceptor { chain ->
            val newRequest = chain.request().newBuilder().build()
            var response = chain.proceed(newRequest)
            try {
                if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED || response.code == HttpURLConnection.HTTP_FORBIDDEN) {
                    response.close()
                }
                if (response.isSuccessful.not()) {
                    response = makeCustomErrorResponse(response, newRequest)
                }
                response
            } catch (e: Exception) {
                Response.Builder().request(newRequest).protocol(Protocol.HTTP_1_1).code(599)
                    .message("News api fail url:${newRequest.url} message: ${e.message}")
                    .body("{${e}}".toResponseBody(null)).build()
            }

        }
    }
}
