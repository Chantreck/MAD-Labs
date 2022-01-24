package com.tsu.itindr.model.retrofit.refresh

import kotlinx.serialization.Serializable

@Serializable
data class LoginBody(
    val email: String,
    val password: String,
)

@Serializable
data class LoginResponse(
    val accessToken: String,
    val accessTokenExpiredAt: String,
    val refreshToken: String,
    val refreshTokenExpiredAt: String,
)

@Serializable
data class RefreshBody(
    val refreshToken: String,
)