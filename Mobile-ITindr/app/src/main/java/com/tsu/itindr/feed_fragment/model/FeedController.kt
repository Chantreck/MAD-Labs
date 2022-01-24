package com.tsu.itindr.feed_fragment.model

import com.tsu.itindr.chat_activity.model.retrofit.ChatInfo
import com.tsu.itindr.model.retrofit.Network
import com.tsu.itindr.model.retrofit.profile.UserDto
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedController {
    private val api: FeedInterface = Network.retrofit.create(FeedInterface::class.java)

    fun getUsers(
        onSuccess: (users: List<UserDto>) -> Unit,
        onFailure: (message: String) -> Unit,
    ) {
        api.getUsers().enqueue(object : Callback<List<UserDto>> {
            override fun onResponse(call: Call<List<UserDto>>, response: Response<List<UserDto>>) {
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess.invoke(it) }
                } else {
                    val errorBody = response.errorBody()?.string()
                    errorBody?.let {
                        onFailure.invoke(JSONObject(it).getString("message"))
                    }
                }
            }

            override fun onFailure(call: Call<List<UserDto>>, t: Throwable) {
                t.message?.let { onFailure.invoke(it) }
            }
        })
    }

    fun likeUser(
        userId: String,
        onMutual: () -> Unit,
        onSuccess: () -> Unit,
        onFailure: (message: String) -> Unit,
    ) {
        api.likeUser(userId).enqueue(object : Callback<LikeResponse> {
            override fun onResponse(call: Call<LikeResponse>, response: Response<LikeResponse>) {
                if (response.isSuccessful) {
                    if (response.body()?.isMutual == true) {
                        onMutual.invoke()
                    }
                    onSuccess.invoke()
                } else {
                    val errorBody = response.errorBody()?.string()
                    errorBody?.let {
                        onFailure.invoke(JSONObject(it).getString("message"))
                    }
                }
            }

            override fun onFailure(call: Call<LikeResponse>, t: Throwable) {
                t.message?.let { onFailure.invoke(it) }
            }
        })
    }

    fun dislikeUser(
        userId: String,
        onSuccess: () -> Unit,
        onFailure: (message: String) -> Unit,
    ) {
        api.dislikeUser(userId).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    onSuccess.invoke()
                } else {
                    val errorBody = response.errorBody()?.string()
                    errorBody?.let {
                        onFailure.invoke(JSONObject(it).getString("message"))
                    }
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                t.message?.let { onFailure.invoke(it) }
            }
        })
    }

    fun createChat(
        userId: String,
        onSuccess: (response: ChatInfo) -> Unit,
        onFailure: (message: String) -> Unit,
    ) {
        api.createChat(UserShort(userId)).enqueue(object : Callback<ChatInfo> {
            override fun onResponse(call: Call<ChatInfo>, response: Response<ChatInfo>) {
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess.invoke(it) }
                } else {
                    val errorBody = response.errorBody()?.string()
                    errorBody?.let {
                        onFailure.invoke(JSONObject(it).getString("message"))
                    }
                }
            }

            override fun onFailure(call: Call<ChatInfo>, t: Throwable) {
                t.message?.let { onFailure.invoke(it) }
            }
        })
    }
}