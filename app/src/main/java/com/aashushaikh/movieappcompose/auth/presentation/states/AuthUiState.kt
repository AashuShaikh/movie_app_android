package com.aashushaikh.movieappcompose.auth.presentation.states

data class AuthUiState(
    val email: String = "",
    val password: String = "",

    val isEmailValid: Boolean = true,
    val isPasswordValid: Boolean = true,

    val isPasswordVisible: Boolean = false,

    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val isRegistered: Boolean = false,
    val errorMessage: String? = null,
)

