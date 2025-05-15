package com.aashushaikh.movieappcompose.auth.presentation.events

sealed interface AuthUiEvents {

    data class OnLogin(val email: String, val password: String): AuthUiEvents
    data class OnRegister(val email: String, val password: String): AuthUiEvents
    data class OnEmailChanged(val email: String): AuthUiEvents
    data class OnPasswordChanged(val password: String): AuthUiEvents
    data class TogglePasswordVisibility(val visibility: Boolean): AuthUiEvents

}