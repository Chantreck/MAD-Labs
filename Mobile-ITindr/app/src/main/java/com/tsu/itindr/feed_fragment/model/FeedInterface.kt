package com.tsu.itindr.feed_fragment.model

import com.tsu.itindr.chat_activity.model.retrofit.ChatInfo
import com.tsu.itindr.model.retrofit.profile.UserDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FeedInterface {
    @GET("v1/user/feed")
    fun getUsers(): Call<List<UserDto>>

    @POST("v1/user/{userId}/like")
    fun likeUser(@Path("userId") userId: String): Call<LikeResponse>

    @POST("v1/user/{userId}/dislike")
    fun dislikeUser(@Path("userId") userId: String): Call<String>

    @POST("v1/chat")
    fun createChat(@Body body: UserShort): Call<ChatInfo>
}