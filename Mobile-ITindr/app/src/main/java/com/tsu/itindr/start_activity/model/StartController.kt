package com.tsu.itindr.start_activity.model

import com.tsu.itindr.model.retrofit.profile.ProfileInterface
import com.tsu.itindr.model.retrofit.profile.TopicDto
import com.tsu.itindr.model.retrofit.Network
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StartController {
    private val api: ProfileInterface = Network.retrofit.create(ProfileInterface::class.java)

    fun getTopics(
        onSuccess: (response: List<TopicDto>) -> Unit,
        onFailure: () -> Unit,
    ) {
        api.getTopics().enqueue(object : Callback<List<TopicDto>> {
            override fun onResponse(
                call: Call<List<TopicDto>>,
                response: Response<List<TopicDto>>,
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess.invoke(it) }
                } else {
                    onFailure.invoke()
                }
            }

            override fun onFailure(call: Call<List<TopicDto>>, t: Throwable) {
                onFailure.invoke()
            }
        })
    }
}