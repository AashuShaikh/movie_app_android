package com.aashushaikh.movieappcompose.auth.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aashushaikh.movieappcompose.R
import com.aashushaikh.movieappcompose.auth.presentation.events.AuthUiEvents
import com.aashushaikh.movieappcompose.auth.presentation.states.AuthUiState
import com.aashushaikh.movieappcompose.utils.DarkBlue
import com.aashushaikh.movieappcompose.utils.DesertWhite
import com.aashushaikh.movieappcompose.utils.SandYellow

@Composable
fun RegisterScreenRoot(
    authViewModel: AuthViewModel,
    onGotoLoginClick: () -> Unit,
) {
    val authState = authViewModel.authState.collectAsStateWithLifecycle()

    RegisterScreen(
        authState = authState.value,
        onEvent = authViewModel::onEvent,
        onGotoLoginClick = onGotoLoginClick
    )
}

@Composable
fun RegisterScreen(
    authState: AuthUiState,
    onEvent: (AuthUiEvents) -> Unit,
    onGotoLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DarkBlue)
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.app_logo),
            modifier = Modifier.size(250.dp),
            contentDescription = "",
            contentScale = ContentScale.FillBounds
        )
        Surface(
            modifier = Modifier.fillMaxWidth().widthIn(300.dp)
                .height(IntrinsicSize.Min)
                .padding(horizontal = 16.dp),
            color = DesertWhite,
            shape = RoundedCornerShape(20),
            border = BorderStroke(4.dp, color = SandYellow)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().widthIn(max = 300.dp)
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                    value = authState.email,
                    onValueChange = {onEvent(AuthUiEvents.OnEmailChanged(it))},
                    placeholder = {
                        Text(text = "Email")
                    },
                    trailingIcon = {
                        if(authState.email.isNotBlank()){
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear Email"
                            )
                        }
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Email"
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SandYellow,
                        cursorColor = DarkBlue
                    ),
                    shape = RoundedCornerShape(20)
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                    value = authState.password,
                    onValueChange = {onEvent(AuthUiEvents.OnPasswordChanged(it))},
                    placeholder = {
                        Text(text = "Password")
                    },
                    visualTransformation = if(authState.isPasswordVisible){
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
//                    trailingIcon = {
//                        val icon = if(authState.isPasswordVisible) Icons.Default
//                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Password"
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SandYellow,
                        cursorColor = DarkBlue
                    ),
                    shape = RoundedCornerShape(20),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton(
                    onClick = {onEvent(AuthUiEvents.OnRegister(
                        email = authState.email,
                        password = authState.password
                    ))},
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(max = 300.dp)
                        .padding(horizontal = 50.dp),
                ) {
                    Text(
                        text = "Register",
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Text(
                        text = "Already Registered?",
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "Click Here to Login",
                        color = Color.Blue,
                        modifier = Modifier
                            .clickable {
                                onGotoLoginClick()
                            }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview(modifier: Modifier = Modifier) {
    RegisterScreen(
        authState = AuthUiState(),
        onEvent = { },
        onGotoLoginClick = {}
    )
}

