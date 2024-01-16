package com.hacine.mohamed.hakim.flixflex.ui.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.hacine.mohamed.hakim.flixflex.R


@Preview
@Composable
fun LoadingView(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        CircularProgress(
            color = if (isSystemInDarkTheme()) Color.White else Color.Black,
            strokeWidth = 3.dp,
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.Center)
        )
    }
}

@Preview
@Composable
fun CircularProgress(
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 2.dp,
    color: Color = colorResource(id = R.color.purple_500),
    roundDuration: Int = 600,
) {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = roundDuration
            }
        ),
    )
    CircularProgressIndicator(
        progress = 1f,
        modifier = modifier
            .rotate(angle)
            .border(
                strokeWidth,
                brush = Brush.sweepGradient(
                    listOf(
                        Color.Transparent,
                        color.copy(0.1f),
                        color.copy(0.5f),
                        color
                    )
                ),
                shape = CircleShape
            ),
        strokeWidth = strokeWidth,
        color = Color.Transparent
    )
}



@Composable
fun ThreeDotLoading() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.three_dot_loading))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isSystemInDarkTheme() )Color.Black else Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition = composition,
            speed = 1f,
            iterations = Int.MAX_VALUE,
            contentScale = ContentScale.Crop,
            modifier = Modifier.wrapContentSize()
        )
    }

}



