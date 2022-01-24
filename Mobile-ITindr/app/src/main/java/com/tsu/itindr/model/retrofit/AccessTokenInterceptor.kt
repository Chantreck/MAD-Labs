package com.tsu.itindr.model.retrofit

import com.tsu.itindr.model.retrofit.refresh.TokenController
import okhttp3.Interceptor
import okhttp3.Response

class AccessTokenInterceptor(private val controller: TokenController) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (response.code == 401) {
            response.close()
            controller.refreshToken()
            val newRequest = request.newBuilder().build()
            return chain.proceed(newRequest)
        }

        return response
    }
}