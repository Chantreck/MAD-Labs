package com.tsu.itindr.model.retrofit.profile

import com.tsu.itindr.model.retrofit.Network
import com.tsu.itindr.model.utils.SharedPreferencesUtils
import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileController {
    private val api: ProfileInterface = Network.retrofit.create(ProfileInterface::class.java)
    private val imageApi: ProfileInterface =
        Network.multipartRetrofit.create(ProfileInterface::class.java)

    fun getProfile(
        onSuccess: (response: UserDto) -> Unit,
        onFailure: (message: String) -> Unit,
    ) {
        api.getProfile().enqueue(object : Callback<UserDto> {
            override fun onResponse(
                call: Call<UserDto>,
                response: Response<UserDto>,
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

            override fun onFailure(call: Call<UserDto>, t: Throwable) {
                t.message?.let { onFailure.invoke(it) }
            }
        })
    }

    fun getTopics(
        onSuccess: (response: List<TopicDto>) -> Unit,
        onFailure: (message: String) -> Unit,
    ) {
        api.getTopics().enqueue(object : Callback<List<TopicDto>> {
            override fun onResponse(call: Call<List<TopicDto>>, response: Response<List<TopicDto>>) {
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess.invoke(it) }
                } else {
                    val errorBody = response.errorBody()?.string()
                    errorBody?.let {
                        onFailure.invoke(JSONObject(it).getString("message"))
                    }
                }
            }

            override fun onFailure(call: Call<List<TopicDto>>, t: Throwable) {
                t.message?.let { onFailure.invoke(it) }
            }
        })
    }

    fun updateProfile(
        body: ProfileBody,
        onSuccess: (response: UserDto) -> Unit,
        onFailure: (message: String) -> Unit,
    ) {
        api.updateProfile(body).enqueue(object : Callback<UserDto> {
            override fun onResponse(
                call: Call<UserDto>,
                response: Response<UserDto>,
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess.invoke(it) }
                } else {
                    val errorBody = response.errorBody()?.string()
                    errorBody?.let {
                        onFailure.invoke(JSONObject(it).getString("message"))
                    }
                }
            }

            override fun onFailure(call: Call<UserDto>, t: Throwable) {
                t.message?.let { onFailure.invoke(it) }
            }
        })
    }

    fun updateProfilePicture(
        body: MultipartBody.Part,
        onSuccess: () -> Unit,
        onFailure: (message: String) -> Unit,
    ) {
        imageApi.updateProfilePicture(body).enqueue(object : Callback<String> {
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

    fun deleteProfilePicture(
        onSuccess: () -> Unit,
        onFailure: (message: String) -> Unit,
    ) {
        imageApi.deleteProfilePicture().enqueue(object : Callback<String> {
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
}