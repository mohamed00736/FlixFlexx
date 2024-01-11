package com.hacine.mohamed.hakim.flixflex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.hacine.mohamed.hakim.flixflex.presentation.AppNavHost
import com.hacine.mohamed.hakim.flixflex.presentation.SignUpScreen.AuthViewModel
import com.hacine.mohamed.hakim.flixflex.ui.theme.FlixFlexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<AuthViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlixFlexTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {


                    AppNavHost(viewModel )

                }
            }
        }
    }
}

