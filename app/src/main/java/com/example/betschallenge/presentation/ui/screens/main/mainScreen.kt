package com.example.betschallenge.presentation.ui.screens.main


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.betschallenge.R
import com.example.betschallenge.presentation.ui.theme.BlackPrimary
import com.example.betschallenge.presentation.ui.theme.RedPrimary
import com.example.betschallenge.presentation.ui.theme.RedSecundary
import com.example.betschallenge.presentation.ui.theme.WhitePrimary

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}

@Composable
fun MainPage(onRegister: () -> Unit={}, onLogin: () -> Unit={}){
    MainScreen(onRegister = onRegister, onLogin = onLogin)
}

@Composable
fun MainScreen(onRegister: () -> Unit={}, onLogin: () -> Unit={}) {
        val context = LocalContext.current

        Column(
            modifier = Modifier.fillMaxSize().background(RedSecundary),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            BoxWithConstraints {
                val spacerHeight = maxHeight * 0.1f // 10% of theparent's height
                Spacer(modifier = Modifier.height(spacerHeight))
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_app),
                    contentDescription = null,
                    modifier = Modifier.size(220.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        onLogin()
                    },
                    modifier = Modifier
                        .padding(18.dp)
                        .width(240.dp).height(50.dp),
                    shape = RoundedCornerShape(60.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WhitePrimary,
                    )
                ) {
                    Text(
                        fontSize = 16.sp,
                        text = stringResource(id = R.string.label_login),
                        color = BlackPrimary,
                    )
                }

                Button(
                    onClick = {
                        onRegister()
                    },
                    modifier = Modifier
                        .padding(18.dp)
                        .width(240.dp).height(50.dp),
                    shape = RoundedCornerShape(60.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WhitePrimary,
                    )
                ) {
                    Text(
                        fontSize = 16.sp,
                        text = stringResource(id = R.string.label_register),
                        color = BlackPrimary,
                    )
                }
  /*              Image(
                    painter = painterResource(id = R.drawable.sigin_google_button),
                    contentDescription = null,
                    modifier = Modifier
                        .width(220.dp)
                        .height(50.dp)
                )*/

                Spacer(
                    modifier = Modifier.height(
                        20.dp
                    )
                )

                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(6.dp)
                        .background(
                            color = MaterialTheme.colorScheme.onSecondary,
                            shape = RoundedCornerShape(4.dp)
                        )
                )

                Spacer(
                    modifier = Modifier.height(
                        22.dp
                    )
                )

            }
        }
    }