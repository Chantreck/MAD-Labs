package com.tsu.itindr.sign_up_activity.model

import com.tsu.itindr.model.retrofit.refresh.LoginBody
import com.tsu.itindr.model.retrofit.refresh.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterInterface {
    @POST("v1/auth/register")
    fun register(@Body registerParams: LoginBody): Call<LoginResponse>
}