package com.aashushaikh.movieappcompose.auth.presentation.events

sealed class AuthUiEvents {

    data class Login(val email: String, val password: String): AuthUiEvents()
    data class Register(val email: String, val password: String): AuthUiEvents()

}