package com.tsu.itindr.sign_in_activity.model

import com.tsu.itindr.model.retrofit.refresh.LoginBody
import com.tsu.itindr.model.retrofit.refresh.LoginResponse
import com.tsu.itindr.model.retrofit.Network
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginController {
    private val api: LoginInterface = Network.retrofit.create(LoginInterface::class.java)

    fun login(
        loginParams: LoginBody,
        onSuccess: (response: LoginResponse) -> Unit,
        onFailure: (message: String) -> Unit,
    ) {
        api.login(loginParams).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess.invoke(it) }
                } else {
                    val errorBody = response.errorBody()?.string()
                    errorBody?.let {
                        onFailure.invoke(JSONObject(it).getString("message"))
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                t.message?.let { onFailure.invoke(it) }
            }
        })
    }
}
