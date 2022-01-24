package com.tsu.itindr.chat_activity.model.retrofit

import com.tsu.itindr.model.retrofit.Network
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatController {
    private val api = Network.retrofit.create(ChatInterface::class.java)
    private val messageApi = Network.multipartRetrofit.create(ChatInterface::class.java)

    fun sendMessage(
        chatId: String,
        text: RequestBody?,
        images: RequestBody?,
        onSuccess: (response: MessageDto) -> Unit,
        onFailure: (message: String) -> Unit,
    ) {
        messageApi.sendMessage(chatId, text, images).enqueue(object : Callback<MessageDto> {
            override fun onResponse(call: Call<MessageDto>, response: Response<MessageDto>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        onSuccess.invoke(it)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    errorBody?.let {
                        onFailure.invoke(JSONObject(it).getString("message"))
                    }
                }
            }

            override fun onFailure(call: Call<MessageDto>, t: Throwable) {
                t.message?.let { onFailure.invoke(it) }
            }
        })
    }

    suspend fun getMessages(chatId: String, count: Int, offset: Int) =
        api.getMessages(chatId, count, offset)
}