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

val gray = Color(0xFFf1f1f1)
val grayNight = Color(0xFF303030)

val grayText = Color(0xFF111111)
val grayTextNight = Color(0xFFffffff)

val background_content_dark = Color(0xFF222222)
val background_content_light = Color(0xFFEEEEEE)

val darkBackground = Color(0xFF121212)
val SentBubbleColor: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) blue else blueNight

val ReceivedBubbleColor: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) gray else grayNight

val ReceivedTextColor: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) grayText else grayTextNight

val BackgroundColor: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) blueBG else darkBackground

val TextColor: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) Color.Black else Color.White

val CardColor: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) card else Color.Black

val ButtonColor: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) blue else blueNight

val ButtonColo2r: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) Color.White else Color.Black

val ButtonTextColor: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) Color.White else Color.Black

val BottomSheetBackground: Color
    @Composable
    get() = if (isSystemInDarkTheme()) background_content_dark else background_content_light

val DialogNoText: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) blue else Color.White

val SwitcherBackground: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) Color.White else Color.Black

val AppPrimaryColor = Color(0xFF180E36)
val AppPrimaryColor2 = Color(0xFFFFFFFF)
val AppOnPrimaryColor = Color.White.copy(alpha = 0.78F)
val SeeMore = Color(0xFF7E7E7E)