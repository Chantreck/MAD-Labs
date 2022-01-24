package com.tsu.itindr.model.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.tsu.itindr.model.retrofit.refresh.TokenController
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object Network {
    private const val BASE_URL = "http://193.38.50.175/itindr/api/mobile/"
    private val mediaType = "application/json".toMediaType()
    private val multipartMediaType = "multipart/form-data".toMediaType()

    private val refreshClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor(HeaderInterceptor())
        .build()

    @ExperimentalSerializationApi
    val refreshRetrofit: Retrofit = Retrofit.Builder()
        .client(refreshClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(Json.asConverterFactory(mediaType))
        .build()

    private val tokenController = TokenController()

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor(AccessTokenInterceptor(tokenController))
        .addInterceptor(HeaderInterceptor())
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    @ExperimentalSerializationApi
    val retrofit: Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL)
        .addConverterFactory(Json.asConverterFactory(mediaType))
        .build()

    @ExperimentalSerializationApi
    val multipartRetrofit: Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL)
        .addConverterFactory(Json.asConverterFactory(multipartMediaType))
        .build()
}