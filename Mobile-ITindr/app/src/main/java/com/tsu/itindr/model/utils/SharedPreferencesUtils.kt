package com.tsu.itindr.model.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.tsu.itindr.model.retrofit.refresh.LoginResponse

object SharedPreferencesUtils {
    private const val PREFERENCES = "ITindrTokens"
    private const val ACCESS_TOKEN_KEY = "ACCESS_TOKEN_KEY"
    private const val REFRESH_TOKEN_KEY = "REFRESH_TOKEN_KEY"
    private const val USER_ID = "USER_ID"
    private var preferences: SharedPreferences? = null

    fun setup(context: Context) {
        preferences = context.getSharedPreferences(PREFERENCES, MODE_PRIVATE)
    }

    fun saveTokens(response: LoginResponse) {
        val instance = preferences ?: return
        with(instance.edit()) {
            putString(ACCESS_TOKEN_KEY, response.accessToken)
            putString(REFRESH_TOKEN_KEY, response.refreshToken)
            apply()
        }
    }

    fun clearInfo() {
        val instance = preferences ?: return
        instance.edit()
            .clear()
            .apply()
    }

    fun saveUserId(userId: String) {
        val instance = preferences ?: return
        instance.edit()
            .putString(USER_ID, userId)
            .apply()
    }

    fun getAccessToken(): String {
        val instance = preferences ?: return ""
        return instance.getString(ACCESS_TOKEN_KEY, "") ?: ""
    }

    fun getRefreshToken(): String {
        val instance = preferences ?: return ""
        return instance.getString(REFRESH_TOKEN_KEY, "") ?: ""
    }

    fun getUserId(): String {
        val instance = preferences ?: return ""
        return instance.getString(USER_ID, "") ?: ""
    }
}