package com.example.alpura.api

import com.example.alpura.screens.article.Article
import com.example.alpura.screens.comment.CommentCreationDto
import com.example.alpura.screens.comment.CommentResponseDto
import com.example.alpura.screens.education.VideoModuleResponseDto
import com.example.alpura.screens.home.UserResponseDto
import com.example.alpura.screens.test.TestQuestion
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthApiService {
    @POST("api/v1/auth/register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<Unit>

    @POST("api/v1/auth/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<Unit>
}

object RetrofitClientAuth{
    private const val BASE_URL = "http://192.168.232.207:8084/"

    val apiService: AuthApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApiService::class.java)
    }
}

interface UserApiService {
    @GET("api/v1/user/get-user-information")
    suspend fun getUserByEmail(@Query("email") email: String): UserResponseDto
}

object RetrofitClientUser{
    private const val BASE_URL = "http://192.168.232.207:8082/"

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

    @GET("api/v1/article/get-article/{id}")
    suspend fun getArticleById(@Path("id") id: String): Article
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

interface CommentApiService {

    @GET("api/v1/comment/get-comments")
    suspend fun getComments(@Query("articleId") articleId: Long): List<CommentResponseDto>

    @POST("api/v1/comment/add-comment")
    suspend fun postComment(@Body comment: CommentCreationDto): CommentResponseDto
}

object RetrofitClientComment {
    private const val BASE_URL = "http://192.168.232.207:8081/"

    val apiService: CommentApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CommentApiService::class.java)
}

interface TestApiService {

    @GET("api/v1/test/get-tests/{articleId}")
    suspend fun getTests(@Path("articleId") articleId: Long): List<TestQuestion>
}

object RetrofitClientTest {
    private const val BASE_URL = "http://192.168.232.207:8081/"

    val apiService: TestApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TestApiService::class.java)
}

interface VideoApiService {
    @GET("api/v1/video-module/get-all-videos")
    suspend fun getAllVideos(): List<VideoModuleResponseDto>

    @GET("api/v1/video-module/get-video/{id}")
    suspend fun getVideoById(@Path("id") id: Long): VideoModuleResponseDto
}

object RetrofitClientEducation {
    private const val BASE_URL = "http://192.168.232.207:8083/"

    val apiService: VideoApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(VideoApiService::class.java)
}

