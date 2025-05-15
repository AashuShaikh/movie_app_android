package com.aashushaikh.movieappcompose.auth.data.remote

import com.aashushaikh.movieappcompose.auth.data.models.request_models.AuthRequestDto
import com.aashushaikh.movieappcompose.auth.data.models.response_models.AuthNetworkResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/login")
    suspend fun login(
        @Body body: AuthRequestDto
    ): Response<AuthNetworkResponseDto>

    @POST("auth/register")
    suspend fun register(
        @Body body: AuthRequestDto
    ): Response<Unit>
}