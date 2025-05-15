package com.aashushaikh.movieappcompose.auth.domain.repositories

import retrofit2.Response

interface AuthRepository {

    suspend fun login(email: String, password: String): Response<Boolean>
    suspend fun register(email: String, password: String): Response<Boolean>
    suspend fun refresh(refreshToken: String): Response<Boolean>

}