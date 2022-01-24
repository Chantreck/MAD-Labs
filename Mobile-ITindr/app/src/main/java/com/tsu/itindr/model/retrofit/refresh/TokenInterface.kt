package com.tsu.itindr.model.retrofit.refresh

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenInterface {
    @POST("v1/auth/refresh")
    fun refreshToken(@Body body: RefreshBody): Call<LoginResponse>
}