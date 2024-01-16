package com.hacine.mohamed.hakim.flixflex.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val blueBG = Color(0xFFF4F7FD)
val blueText = Color(0xFF1E3054)
val card = Color(0xFFFFFFFF)

val blue = Color(0xFF006AF6)
val blueNight = Color(0xFF147EFF)
val RedColor = Color(0xFFFE554A)


val TextColor: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) Color.Black else Color.White

val CardColor: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) card else Color.Black



val ButtonColo2r: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) Color.White else Color.Black



val AppOnPrimaryColor = Color.White.copy(alpha = 0.78F)
