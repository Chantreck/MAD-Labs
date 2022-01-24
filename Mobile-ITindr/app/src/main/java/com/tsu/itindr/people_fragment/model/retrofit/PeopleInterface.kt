package com.tsu.itindr.people_fragment.model.retrofit

import com.tsu.itindr.model.retrofit.profile.UserDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PeopleInterface {
    @GET("v1/user")
    suspend fun getUsers(@Query("limit") limit: Int, @Query("offset") offset: Int): Response<List<UserDto>>
}