package com.nntsl.data.network.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.nntsl.data.network.UsersNetworkDataSource
import com.nntsl.data.network.model.SecondSourceUserResponse
import com.nntsl.data.network.model.SecondUserResponse
import com.nntsl.data.network.model.UserListResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Url
import javax.inject.Inject
import javax.inject.Singleton

const val firstSource = "https://api.dailymotion.com/users"
const val secondSource = "https://api.github.com/users"

const val firstBaseUrl = "https://api.dailymotion.com/"
const val secondBaseUrl = "https://api.github.com/"

private interface RetrofitUsersNetworkApi {

    @GET("users")
    suspend fun getFirstUsers(): UserListResponse

    @GET("users")
    suspend fun getSecondUsers(): List<SecondSourceUserResponse>

    @GET
    suspend fun getUserByUrl(@Url url: String): SecondUserResponse
}

private val json: Json by lazy {
    Json {
        this.ignoreUnknownKeys = true
    }
}

@Singleton
class RetrofitUsersNetwork @Inject constructor() : UsersNetworkDataSource {

    @OptIn(ExperimentalSerializationApi::class)
    private val firstNetworkApi by lazy {
        Retrofit.Builder()
            .baseUrl(firstBaseUrl)
            .client(
                OkHttpClient.Builder()
                    .apply {
                        addInterceptor(
                            HttpLoggingInterceptor()
                                .apply { level = HttpLoggingInterceptor.Level.BODY }
                        )
                    }
                    .build()
            )
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(RetrofitUsersNetworkApi::class.java)
    }

    @OptIn(ExperimentalSerializationApi::class)
    private val secondNetworkApi by lazy {
        Retrofit.Builder()
            .baseUrl(secondBaseUrl)
            .client(
                OkHttpClient.Builder()
                    .apply {
                        addInterceptor(
                            HttpLoggingInterceptor()
                                .apply { level = HttpLoggingInterceptor.Level.BODY }
                        )
                    }
                    .build()
            )
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(RetrofitUsersNetworkApi::class.java)
    }

    override suspend fun getFirstUsers(): Flow<UserListResponse> {
        return flow {
            emit(firstNetworkApi.getFirstUsers())
        }
    }

    override suspend fun getSecondUsers(): Flow<List<SecondUserResponse>> {
        return flow {
            emit(
                secondNetworkApi.getSecondUsers().map {
                    secondNetworkApi.getUserByUrl(it.url)
                }
            )
        }
    }
}
