package com.example.betschallenge.presentation.ui.screens.register

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
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
import androidx.compose.ui.text.input.KeyboardType
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

@Preview
@Composable
fun RegisterPreview() {
    RegisterScreen()
}

@Composable
fun RegisterPage(viewModel: RegisterViewModel = hiltViewModel(), registerSuccess: () -> Unit = {}) {
    val registerState = viewModel.registerState.collectAsState()
    val context: Context = LocalContext.current
    LaunchedEffect(key1 = registerState.value) {
        when (val state = registerState.value) {
            is Resource.Success -> {
                registerSuccess()
                viewModel.registerEventConsumed()
            }

            is Resource.Error -> {
                Toast.makeText(context, "Error: ${state.message}", Toast.LENGTH_LONG).show()
                viewModel.registerEventConsumed()
            }

            else -> {}
        }
    }
    RegisterScreen(onRegister = { email, password ->
        viewModel.createUser(email, password)
    }, context = context, uiState = registerState.value)
}

@SuppressLint("SimpleDateFormat")
@Composable
fun RegisterScreen(
    onRegister: (String, String) -> Unit = { _, _ -> },
    context: Context = LocalContext.current,
    uiState: Resource<UserEntity> = Resource.Idle,
) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

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
                text = "Registrar nuevo Usuario",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(32.dp))
            CustomTextField(
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = rememberVectorPainter(Icons.Default.Person),
                value = nombre,
                onValueChange = {
                    nombre = it
                },
                label = "Nombre "
            )
            Spacer(modifier = Modifier.height(8.dp))
            CustomTextField(
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = rememberVectorPainter(Icons.Default.Email),
                value = email,
                keyBoarType = KeyboardType.Email,
                onValueChange = {
                    email = it
                },
                label = stringResource(R.string.label_email)
            )
            Spacer(modifier = Modifier.height(8.dp))

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


            var validationMessage by remember { mutableStateOf("") }
            fun validateInputs(): Boolean {
                return (nombre.isNotBlank() && email.isNotBlank()
                        && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
                        && password.isNotBlank()
                        && password.length >= 6).apply {
                    if (!this) {
                        validationMessage = when {
                            nombre.isBlank() -> "Ingresa tu nombre"
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
                    // Crear el objeto Client y registrar
                    onRegister(email, password)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(text = "Registrar", color = Color.White)
            }


        }
        LoadingScreen(isShowingDialog = uiState is Resource.Loading)
    }
}