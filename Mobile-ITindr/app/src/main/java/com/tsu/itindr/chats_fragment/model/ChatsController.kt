package com.tsu.itindr.chats_fragment.model

import com.tsu.itindr.chat_activity.model.retrofit.ChatDto
import com.tsu.itindr.chat_activity.model.retrofit.ChatInterface
import com.tsu.itindr.model.retrofit.Network
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatsController {
    private val api = Network.retrofit.create(ChatInterface::class.java)

    fun getChats(
        onSuccess: (response: List<ChatDto>) -> Unit,
        onFailure: (message: String) -> Unit,
    ) {
        api.getChats().enqueue(object : Callback<List<ChatDto>> {
            override fun onResponse(
                call: Call<List<ChatDto>>,
                response: Response<List<ChatDto>>,
            ) {
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

            override fun onFailure(call: Call<List<ChatDto>>, t: Throwable) {
                t.message?.let { onFailure.invoke(it) }
            }
        })
    }
}