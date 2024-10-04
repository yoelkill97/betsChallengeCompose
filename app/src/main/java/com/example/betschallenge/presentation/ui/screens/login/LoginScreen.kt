package com.example.betschallenge.presentation.ui.screens.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.betschallenge.R
import com.example.betschallenge.domain.entity.Resource
import com.example.betschallenge.domain.entity.user.UserEntity
import com.example.betschallenge.presentation.ui.components.CustomTextField
import com.example.betschallenge.presentation.ui.components.LoadingScreen

@Preview(showBackground = false)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}

@Composable
fun LoginPage(
    loginViewModel: LoginViewModel = hiltViewModel(),
    loginSuccess: () -> Unit = {}
) {
    val loginUIState = loginViewModel.loginState.collectAsState()
    val context: Context = LocalContext.current
    LaunchedEffect(key1 = loginUIState.value) {
        when (val state = loginUIState.value) {
            is Resource.Success -> {
                loginSuccess()
                loginViewModel.loginEventConsumed()
            }

            is Resource.Error -> {
                Toast.makeText(context, "Error: ${state.message}", Toast.LENGTH_LONG).show()
                loginViewModel.loginEventConsumed()
            }

            else -> {}
        }

    }
    LoginScreen(
        uiState = loginUIState.value,
        onLoginExecute = { email, password ->
            loginViewModel.login(email, password)
        })
}

@Composable
fun LoginScreen(
    uiState: Resource<UserEntity> = Resource.Idle,
    onLoginExecute: (String, String) -> Unit = { _, _ -> }
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var validationMessage by remember { mutableStateOf("") }
    val context: Context = LocalContext.current
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background, // Set background color
        tonalElevation = 2.dp // Add elevation
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_bet),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Iniciar Sesión",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(32.dp))
            // Campo de Correo

            CustomTextField(
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = rememberVectorPainter(Icons.Default.Person),
                value = email,
                onValueChange = {
                    email = it
                },
                label = stringResource(id = R.string.label_email),
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Campo de Contraseña

            CustomTextField(
                Modifier.fillMaxWidth(),
                leadingIcon = painterResource(id = R.drawable.candado),
                value = password,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                onValueChange = {
                    password = it
                },
                label = stringResource(id = R.string.label_pass),
                trailingIcon = {
                    Icon(
                        painter = if (passwordVisible) painterResource(id = R.drawable.password_show) else painterResource(
                            id = R.drawable.password_hide
                        ),
                        contentDescription = null,
                        modifier = Modifier.clickable { passwordVisible = !passwordVisible }
                    )
                },
            )
            Spacer(modifier = Modifier.height(32.dp))
            fun validateInputs(): Boolean {
                return ( email.isNotBlank()
                        && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
                        && password.isNotBlank()
                        && password.length >= 6).apply {
                    if (!this) {
                        validationMessage = when {
                            email.isBlank() -> "Ingresa tu email"
                            !android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                                .matches() -> "Ingresa un email válido"
                            password.isBlank() -> "Ingresa tu contraseña"
                            password.length < 6 -> "La contraseña debe tener al menos 6 caracteres"
                            else -> "" // All valid
                        }
                    }
                }
            }
            Button(
                onClick = {
                    if (!validateInputs()) {
                        Toast.makeText(
                            context,
                            validationMessage, Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }
                    onLoginExecute(email, password)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(text = "Ingresar", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de Regreso
            /*        TextButton(onClick = { backPressed()}) {
                    Text("Back", color = Color.Blue)
                }*/
        }
        LoadingScreen(isShowingDialog = uiState is Resource.Loading)
    }
}