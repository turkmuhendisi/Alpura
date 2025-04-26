package com.example.alpura.api

import com.example.alpura.screens.article.Article
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("user/register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<Unit>

    @POST("user/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<Unit>

    @GET("api/v1/article/get-all-articles")
    suspend fun getAllArticles(): List<Article>
}

object RetrofitClient {
    private const val BASE_URL = "http://192.168.251.207:8080/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
