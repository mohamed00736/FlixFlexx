package com.hacine.mohamed.hakim.flixflex.presentation.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import com.hacine.mohamed.hakim.flixflex.presentation.signup.AuthViewModel


import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hacine.mohamed.hakim.flixflex.ui.components.LoadingView
import com.hacine.mohamed.hakim.flixflex.ui.theme.CardColor

import com.hacine.mohamed.hakim.flixflex.utils.Resource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: AuthViewModel?,
    onNavigateToSignUp: () -> Unit,
    onNavigateToMovies: () -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    val loginFlow = viewModel?.loginFlow?.collectAsState()
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = "Log In", color = Color.Black
            )
        }, modifier = Modifier.shadow(elevation = 16.dp),
            colors = TopAppBarDefaults.topAppBarColors(containerColor = CardColor),)
    }) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            item {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 16.dp)) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                        },
                        label = {
                            Text(text = "email")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                        },
                        label = {
                            Text(text = "Password")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            viewModel?.login(email, password)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        Text(
                            text = "LOGIN",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        modifier = Modifier
                            .padding(bottom = 30.dp)
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clickable {
                                onNavigateToSignUp()
                            },
                        text = "I dont have account",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    loginFlow?.value?.let {
                        when (it) {
                            is Resource.Failure -> {
                                LaunchedEffect(Unit) {
                                    Toast.makeText(context, it.exception.message, Toast.LENGTH_LONG)
                                        .show()
                                }
                            }
                            Resource.Loading -> {
                                LoadingView()
                            }
                            is Resource.Success -> {
                                LaunchedEffect(Unit) {
                                    onNavigateToMovies()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

