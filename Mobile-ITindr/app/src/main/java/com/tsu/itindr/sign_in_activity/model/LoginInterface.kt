package com.tsu.itindr.sign_in_activity.model

import com.tsu.itindr.model.retrofit.refresh.LoginBody
import com.tsu.itindr.model.retrofit.refresh.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginInterface {
    @POST("v1/auth/login")
    fun login(@Body loginParams: LoginBody): Call<LoginResponse>
}