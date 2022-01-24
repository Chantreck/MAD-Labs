package com.tsu.itindr.model.retrofit

import com.tsu.itindr.model.utils.SharedPreferencesUtils
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .header("Authorization", "Bearer " + SharedPreferencesUtils.getAccessToken())
            .build()
        return chain.proceed(newRequest)
    }
}