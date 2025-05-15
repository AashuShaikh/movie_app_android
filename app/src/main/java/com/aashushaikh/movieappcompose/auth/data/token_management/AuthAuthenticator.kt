package com.aashushaikh.movieappcompose.auth.data.token_management

import android.util.Log
import com.aashushaikh.movieappcompose.auth.data.models.request_models.RefreshTokenRequestDto
import com.aashushaikh.movieappcompose.auth.domain.JwtTokenManager
import com.aashushaikh.movieappcompose.auth.data.remote.RefreshTokenService
import com.aashushaikh.movieappcompose.utils.RefreshService
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val tokenManager: JwtTokenManager,
    @RefreshService private val refreshTokenService: RefreshTokenService
) : Authenticator {
    companion object {
        const val HEADER_AUTHORIZATION = "Authorization"
        const val TOKEN_TYPE = "Bearer"
    }
    override fun authenticate(route: Route?, response: Response): Request? {
        Log.d("Auth", "AuthAuthenticator authenticate called")
        val currentToken = runBlocking {
            tokenManager.getAccessToken()
        }
        synchronized(this) {
            val updatedToken = runBlocking {
                tokenManager.getAccessToken()
            }
            Log.d("Auth", "AuthAuthenticator authenticate: currentToken: $currentToken")
            Log.d("Auth", "AuthAuthenticator authenticate: updatedToken: $updatedToken")
            val token = if (currentToken != updatedToken) {
                updatedToken
            } else {
                Log.d("Auth", "AuthAuthenticator authenticate else")
                val refreshToken = runBlocking { tokenManager.getRefreshToken() }
                val newSessionResponse = runBlocking { refreshTokenService.refreshToken(
                    RefreshTokenRequestDto(refreshToken!!)
                ) }
                if (newSessionResponse.isSuccessful && newSessionResponse.body() != null) {
                    newSessionResponse.body()?.let { body ->
                        runBlocking {
                            tokenManager.saveAccessToken(body.accessToken)
                            tokenManager.saveRefreshToken(body.refreshToken)
                        }
                        body.accessToken
                    }
                } else null
            }
            Log.d("Auth", "AuthAuthenticator authenticate token final: $token")
            return if (token != null) response.request.newBuilder()
                .header(HEADER_AUTHORIZATION, "$TOKEN_TYPE $token")
                .build() else null
        }
    }
}
