package com.tsu.itindr.model.retrofit.profile

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ProfileInterface {
    @GET("v1/profile")
    fun getProfile(): Call<UserDto>

    @GET("v1/topic")
    fun getTopics(): Call<List<TopicDto>>

    @PATCH("v1/profile")
    fun updateProfile(@Body body: ProfileBody): Call<UserDto>

    @Multipart
    @POST("v1/profile/avatar")
    fun updateProfilePicture(@Part body: MultipartBody.Part): Call<String>

    @DELETE("v1/profile/avatar")
    fun deleteProfilePicture(): Call<String>
}