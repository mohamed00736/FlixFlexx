package com.hacine.mohamed.hakim.flixflex.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hacine.mohamed.hakim.flixflex.R

@Composable
fun FailedView(
    onRetry: (() -> Unit)? = null,
    retryable: Boolean = true,
    modifier: Modifier = Modifier ,
    errorText:String?
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                modifier = Modifier

                    .padding(vertical = 16.dp)
                    .size(50.dp)
                    .clickable {
                        if (onRetry != null) {
                            onRetry()
                        }
                    },
                painter = painterResource(id = R.drawable.ic_retry),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary
            )
            Row(
                modifier = Modifier.padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = ("$errorText  ") ?: "somthing went wrong ",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.secondary,
                        fontStyle = MaterialTheme.typography.labelMedium.fontStyle
                    ),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                )
                if (retryable) {
                    Text(
                        modifier = Modifier.clickable(onClick = { onRetry?.invoke() }),
                        text = "Retry Please",
                        style = TextStyle(
                            textDecoration = TextDecoration.Underline,
                            color = MaterialTheme.colorScheme.secondary,
                            fontStyle = MaterialTheme.typography.labelMedium.fontStyle,
                            fontSize = 16.sp),
                        fontWeight = FontWeight.W600,

                    )
                }

            }
        }
    }

}