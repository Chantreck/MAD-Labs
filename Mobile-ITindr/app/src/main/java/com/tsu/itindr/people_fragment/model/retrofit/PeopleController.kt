package com.tsu.itindr.people_fragment.model.retrofit

import com.tsu.itindr.model.retrofit.profile.UserDto
import com.tsu.itindr.model.retrofit.Network
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PeopleController {
    private val api: PeopleInterface = Network.retrofit.create(PeopleInterface::class.java)

    suspend fun getUsers(limit: Int, offset: Int) = api.getUsers(limit, offset)
}