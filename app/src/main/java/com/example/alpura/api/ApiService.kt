package com.example.alpura.api

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("user/register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<Unit>

    @POST("user/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<Unit>
}

object RetrofitClient {
    private const val BASE_URL = "http://192.168.15.207:8080/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
