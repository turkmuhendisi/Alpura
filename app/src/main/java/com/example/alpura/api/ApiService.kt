package com.example.alpura.api

import com.example.alpura.screens.article.Article
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApiService {
    @POST("api/v1/auth/register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<Unit>

    @POST("api/v1/auth/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<Unit>
}

object RetrofitClientUser{
    private const val BASE_URL = "http://192.168.232.207:8084/"

    val apiService: UserApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApiService::class.java)
    }
}


interface ArticleApiService {

    @GET("api/v1/article/get-all-articles")
    suspend fun getAllArticles(): List<Article>
}
object RetrofitClientArticle {
    private const val BASE_URL = "http://192.168.232.207:8081/"

    val apiService: ArticleApiService
        =Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ArticleApiService::class.java)

}
