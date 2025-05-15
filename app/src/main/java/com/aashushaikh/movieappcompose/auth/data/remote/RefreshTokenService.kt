package com.aashushaikh.movieappcompose.auth.data.remote

import com.aashushaikh.movieappcompose.auth.data.models.request_models.RefreshTokenRequestDto
import com.aashushaikh.movieappcompose.auth.data.models.response_models.AuthNetworkResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshTokenService {
    @POST("auth/refresh")
    suspend fun refreshToken(
        @Body refreshTokenRequest: RefreshTokenRequestDto
    ): Response<AuthNetworkResponseDto>
}

