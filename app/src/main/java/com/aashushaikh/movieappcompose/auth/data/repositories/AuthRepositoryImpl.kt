package com.aashushaikh.movieappcompose.auth.data.repositories

import android.util.Log
import com.aashushaikh.movieappcompose.auth.data.models.request_models.AuthRequestDto
import com.aashushaikh.movieappcompose.auth.data.models.request_models.RefreshTokenRequestDto
import com.aashushaikh.movieappcompose.auth.data.remote.AuthService
import com.aashushaikh.movieappcompose.auth.domain.JwtTokenManager
import com.aashushaikh.movieappcompose.auth.data.remote.RefreshTokenService
import com.aashushaikh.movieappcompose.auth.domain.repositories.AuthRepository
import com.aashushaikh.movieappcompose.utils.PublicService
import com.aashushaikh.movieappcompose.utils.RefreshService
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    @RefreshService private val refreshTokenService: RefreshTokenService,
    private val tokenManager: JwtTokenManager
) : AuthRepository {
    override suspend fun login(email: String, password: String): Response<Boolean> {
        Log.d("Auth", "Auth Repo Login")
        try {
            val request = AuthRequestDto(email, password)
            val response = authService.login(request)

            if (response.isSuccessful && response.body() != null) {
                response.body()?.let { body ->
                    Log.d("Auth", "Response body: ${body}")
                    tokenManager.saveAccessToken(body.accessToken)
                    tokenManager.saveRefreshToken(body.refreshToken)
                }
                Log.d("Auth", "Auth Repo Login saved token: access: ${tokenManager.getAccessToken()} and refresh: ${tokenManager.getRefreshToken()}")
                return Response.success(true)
            }

            return Response.error(response.code(), response.errorBody() ?: "".toResponseBody())
        }
        catch (e: Exception){
            return Response.error(500, "".toResponseBody())
        }
    }

    override suspend fun register(email: String, password: String): Response<Boolean> {
        val request = AuthRequestDto(email, password)
        val response = authService.register(request)

        if(response.isSuccessful){
            return Response.success(true)
        }
        return Response.error(response.code(), response.errorBody() ?: "".toResponseBody())
    }

    override suspend fun refresh(refreshToken: String): Response<Boolean> {
        Log.d("Auth", "Auth Repo Refresh")
        val request = RefreshTokenRequestDto(refreshToken)
        val response = refreshTokenService.refreshToken(request)
        Log.d("Auth", "Auth Repo Refresh response: $response")

        if (response.isSuccessful && response.body() != null) {
            response.body()?.let { body ->
                tokenManager.saveAccessToken(body.accessToken)
                tokenManager.saveRefreshToken(body.refreshToken)
            }
            Log.d("Auth", "Auth Repo Refresh saved token: access: ${tokenManager.getAccessToken()} and refresh: ${tokenManager.getRefreshToken()}")
            return Response.success(true)
        }

        return Response.error(response.code(), response.errorBody() ?: "".toResponseBody())
    }

}