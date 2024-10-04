package com.example.betschallenge.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.betschallenge.R

// Set of Material typography styles to start with
// Define tu tipografía personalizada
val ApuestaTotalFontFamily = FontFamily(
    Font(R.font.roboto_regular , FontWeight.Normal),
    Font(R.font.roboto_bold, FontWeight.Bold)
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = ApuestaTotalFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = ApuestaTotalFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    ),
    titleLarge = TextStyle(
        fontFamily = ApuestaTotalFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    labelLarge = TextStyle(
        fontFamily = ApuestaTotalFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    )
)
val textFieldTextStyle = TextStyle(
    fontSize = 14.sp,
    fontWeight = FontWeight.Light
)