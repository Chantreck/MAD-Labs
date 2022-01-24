package com.tsu.itindr.model.retrofit.refresh

import com.tsu.itindr.model.utils.SharedPreferencesUtils
import com.tsu.itindr.model.retrofit.Network

class TokenController {
    private val api: TokenInterface = Network.refreshRetrofit.create(TokenInterface::class.java)

    fun refreshToken() {
        val body = RefreshBody(SharedPreferencesUtils.getRefreshToken())
        val response = api.refreshToken(body).execute()

        if (response.isSuccessful) {
            response.body()?.let {
                SharedPreferencesUtils.saveTokens(it)
            }
        } else {
            SharedPreferencesUtils.clearInfo()
        }
    }
}