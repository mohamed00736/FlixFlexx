package com.hacine.mohamed.hakim.flixflex.presentation.signup

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hacine.mohamed.hakim.flixflex.data.network.AuthUiState
import com.hacine.mohamed.hakim.flixflex.ui.components.LoadingView
import com.hacine.mohamed.hakim.flixflex.ui.theme.CardColor
import com.hacine.mohamed.hakim.flixflex.ui.theme.TextColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    viewModel: AuthViewModel?,
    onNavigateToLogIn: () -> Unit,

    ) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val signupFlow = viewModel?.signupFlow?.collectAsState()

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = "Sign Up", color = TextColor
            )
        }, navigationIcon = {
            IconButton(onClick = {
                onNavigateToLogIn.invoke()

            }) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Back",
                    tint = CardColor
                )

            }
        } , modifier = Modifier.shadow(elevation = 16.dp),colors = TopAppBarDefaults.topAppBarColors(containerColor = CardColor))
    } , containerColor = CardColor) { padding ->

        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding)) {
            item {
                Column(modifier = Modifier.fillMaxSize() ,
                    verticalArrangement = Arrangement.Center ,
                    horizontalAlignment = Alignment.CenterHorizontally) {


                    OutlinedTextField(value = name,
                        onValueChange = {
                            name = it
                        },
                        label = {
                            Text(text = "enter name ")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .wrapContentHeight(),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = email, onValueChange = {
                        email = it
                    }, label = {
                        Text(text = "enter email")
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .wrapContentHeight(),

                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = password,
                        onValueChange = {
                            password = it
                        },
                        label = {
                            Text(text = "enter password")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .wrapContentHeight(),

                        visualTransformation = PasswordVisualTransformation(),
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
                            viewModel?.signup(name, email, password)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .wrapContentHeight(),

                        ) {
                        Text(
                            text = "sign up", style = MaterialTheme.typography.titleMedium
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        modifier = Modifier
                            .padding(bottom = 30.dp)
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clickable {
                                onNavigateToLogIn.invoke()

                            },
                        text = "I already have account",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    signupFlow?.value?.let { it ->
                        when (it) {
                            is AuthUiState.Failure -> {
                                LaunchedEffect(true ){
                                    Toast.makeText(context, it.exception.message, Toast.LENGTH_LONG)
                                        .show()
                                }

                            }

                            AuthUiState.Loading -> {

                                LoadingView()

                            }

                            is AuthUiState.Success -> {

                                LaunchedEffect(true ){
                                    Log.d(
                                        "SignUpScreen",
                                        "SignUpScreen: " + "Am exucting now navigating to login "
                                    )
                                    onNavigateToLogIn.invoke()


                                    Toast.makeText(context, "sucess sign up", Toast.LENGTH_LONG).show()
                                }

                            }

                        }


                    }
                }

            }
        }
    }
}













