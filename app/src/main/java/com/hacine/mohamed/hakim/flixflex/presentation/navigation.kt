package com.hacine.mohamed.hakim.flixflex.presentation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hacine.mohamed.hakim.flixflex.presentation.SignUpScreen.AuthViewModel
import com.hacine.mohamed.hakim.flixflex.presentation.SignUpScreen.SignUpScreen


@Composable
fun AppNavHost(
    viewModel: AuthViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "signup"
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {

        composable("signup") {
            SignUpScreen(navController,viewModel)
        }
    }
}