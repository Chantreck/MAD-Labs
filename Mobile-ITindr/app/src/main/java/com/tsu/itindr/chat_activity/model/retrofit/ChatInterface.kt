package com.tsu.itindr.chat_activity.model.retrofit

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ChatInterface {
    @GET("v1/chat")
    fun getChats(): Call<List<ChatDto>>

    @GET("v1/chat/{chatId}/message")
    suspend fun getMessages(
        @Path("chatId") chatId: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): Response<List<MessageDto>>

    @Multipart
    @POST("v1/chat/{chatId}/message")
    fun sendMessage(
        @Path("chatId") chatId: String,
        @Part("messageText") messageText: RequestBody? = null,
        @Part("attachments") attachments: RequestBody? = null,
    ): Call<MessageDto>
}