package com.aashushaikh.movieappcompose.auth.domain

interface JwtTokenManager {

    suspend fun saveAccessToken(token: String)
    suspend fun saveRefreshToken(token: String)
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun clearAllTokens()

}