package com.aashushaikh.movieappcompose.auth.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aashushaikh.movieappcompose.auth.domain.JwtTokenManager
import com.aashushaikh.movieappcompose.auth.domain.repositories.AuthRepository
import com.aashushaikh.movieappcompose.auth.presentation.events.AuthUiEvents
import com.aashushaikh.movieappcompose.auth.presentation.states.AuthUiState
import com.aashushaikh.movieappcompose.utils.AppConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenManager: JwtTokenManager
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthUiState>(AuthUiState())
    val authState: StateFlow<AuthUiState> = _authState


    init {
        checkIfUserIsLoggedIn()
    }

    fun onEvent(event: AuthUiEvents){
        when(event){
            is AuthUiEvents.OnLogin -> {
                login(email = event.email, password = event.password)
                updateEmail("")
                updatePassword("")
            }

            is AuthUiEvents.OnRegister -> {
                register(email = event.email, password = event.password)
                updateEmail("")
                updatePassword("")
            }

            is AuthUiEvents.OnEmailChanged -> {
                updateEmail(event.email)
            }
            is AuthUiEvents.OnPasswordChanged -> {
                updatePassword(event.password)
            }
            is AuthUiEvents.TogglePasswordVisibility -> {
                _authState.update {
                    it.copy(isPasswordVisible = event.visibility)
                }
            }
        }
    }

    private fun updatePassword(password: String) {
        _authState.update {
            it.copy(password = password)
        }
    }

    private fun updateEmail(email: String) {
        _authState.update {
            it.copy(email = email)
        }
    }

    private fun checkIfUserIsLoggedIn() {
        viewModelScope.launch {
            val token = tokenManager.getAccessToken()
            _authState.update {
                it.copy(
                    isAuthenticated = !token.isNullOrEmpty() // do not check expiry
                )
            }
            Log.d("Auth", "Checking the token: $token")
        }
    }

    fun login(email: String, password: String) {
        Log.d("Auth", "Logging in the user")
        _authState.update {
            it.copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            val result = authRepository.login(email, password)
            if (result.isSuccessful) {
                result.body()?.let {body ->
                    _authState.update {
                        it.copy(
                            isAuthenticated = body,
                        )
                    }
                }
            } else {
                _authState.update {
                    it.copy(
                        errorMessage = "${result.code()}: ${result.message()}"
                    )
                }
            }
        }
    }

    private fun register(email: String, password: String) {
        _authState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            val result = authRepository.register(email, password)
            if(result.isSuccessful){
                _authState.update {
                    it.copy(isRegistered = true)
                }
            }else{
                _authState.update {
                    it.copy(errorMessage = "${result.code()}: ${result.message()}")
                }
            }
        }
    }
}