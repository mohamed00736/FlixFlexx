package com.hacine.mohamed.hakim.flixflex.presentation.SignUpScreen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: AuthViewModel?
) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val signupFlow = viewModel?.signupFlow?.collectAsState()

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = "Sign Up",
                color = Color.White
            )
        },
            navigationIcon = {
                IconButton(onClick = {
                    navController.navigate("login") {
                        popUpTo("signup") { inclusive = true }
                    }
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )

                }
            })
    }) { padding ->

        LazyColumn(modifier = Modifier.padding(padding)) {
            item {
                Column(modifier = Modifier.fillMaxSize()) {


                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
                        },
                        label = {
                            Text(text = "enter name ")
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

                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                        },
                        label = {
                            Text(text = "enter email")
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

                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                        },
                        label = {
                            Text(text = "enter password")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),

                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        )
                    )

                    Button(
                        onClick = {
                            viewModel?.signup(name, email, password)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),

                        ) {
                        Text(
                            text = "sign up",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }


                    Text(
                        modifier = Modifier
                            .padding(bottom = 30.dp)
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clickable {
                                navController.navigate("login") {
                                    popUpTo("signup") { inclusive = true }
                                }
                            },
                        text = "already have account",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    signupFlow?.value?.let {
                        when (it) {
                            is Resource.Failure -> {
                                val context = LocalContext.current
                                Toast.makeText(context, it.exception.message, Toast.LENGTH_LONG)
                                    .show()
                            }
                            Resource.Loading -> {
                                CircularProgressIndicator(modifier = Modifier.wrapContentSize())
                            }
                            is Resource.Success -> {

                                Text(text = "SUCESS SignUp" )

                            }
                        }
                    }

                }
            }
        }


    }


}










sealed class Resource<out R> {
    data class Success<out R>(val result: R) : Resource<R>()
    data class Failure(val exception: Exception) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun <T> Task<T>.await(): T {
    return suspendCancellableCoroutine { cont ->
        addOnCompleteListener {
            if (it.exception != null) {
                cont.resumeWithException(it.exception!!)
            } else {
                cont.resume(it.result, null)
            }
        }
    }
}








