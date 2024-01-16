package com.hacine.mohamed.hakim.flixflex.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.hacine.mohamed.hakim.flixflex.data.repository.AuthRepository
import com.hacine.mohamed.hakim.flixflex.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


//@HiltViewModel
//class AuthViewModel @Inject constructor(
//    private val repository: AuthRepository
//) : ViewModel() {
//
//    private val _loginFlow = MutableStateFlow<UiState<FirebaseUser>>(UiState.Idle)
//    val loginFlow: StateFlow<UiState<FirebaseUser>> = _loginFlow
//
//    private val _signupFlow = MutableStateFlow<UiState<FirebaseUser>>(UiState.Idle)
//    val signupFlow: StateFlow<UiState<FirebaseUser>> = _signupFlow.asStateFlow()
//
//    val currentUser: FirebaseUser?
//        get() = repository.currentUser
//
//    init {
//        if (repository.currentUser != null) {
//            _loginFlow.value = UiState.Success(repository.currentUser)
//        }
//    }
//
//    fun login(email: String, password: String) = viewModelScope.launch {
//        _loginFlow.value = UiState.Loading
//        val result = repository.login(email, password)
//        if (result is com.hacine.mohamed.hakim.flixflex.data.network.Resource.Success) {
//            _loginFlow.value = UiState.Success(result.value)
//        } else if (result is com.hacine.mohamed.hakim.flixflex.data.network.Resource.Failure) {
//            _loginFlow.value = UiState.Error(result.message)
//        }
//    }
//
//    fun signup(name: String, email: String, password: String) = viewModelScope.launch {
//        _signupFlow.value = UiState.Loading
//        val result = repository.signup(name, email, password)
//        if (result is com.hacine.mohamed.hakim.flixflex.data.network.Resource.Success) {
//            _signupFlow.value = UiState.Success(result.value)
//        } else if (result is com.hacine.mohamed.hakim.flixflex.data.network.Resource.Failure) {
//            _signupFlow.value = UiState.Fail(result.message)
//        }
//    }
//
//    fun logout() {
//        repository.logout()
//        _loginFlow.value = UiState.Idle
//        _signupFlow.value = UiState.Idle
//    }
//}
//
//sealed class UiState<out T> {
//    object Idle : UiState<Nothing>()
//    object Loading : UiState<Nothing>()
//    data class Success<T>(val data: T? = null) : UiState<T>()
//    data class Error(val message: String? = null) : UiState<Nothing>()
//    data class Fail(val message: String? = null) : UiState<Nothing>()
//}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _loginFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val loginFlow: StateFlow<Resource<FirebaseUser>?> = _loginFlow

    private val _signupFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signupFlow: StateFlow<Resource<FirebaseUser>?> = _signupFlow

    val currentUser: FirebaseUser?
        get() = repository.currentUser

    init {
        if (repository.currentUser != null) {
            _loginFlow.value = Resource.Success(repository.currentUser!!)
        }
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        _loginFlow.value = Resource.Loading
        val result = repository.login(email, password)
        _loginFlow.value = result
    }

    fun signup(name: String, email: String, password: String) = viewModelScope.launch {
        _signupFlow.value = Resource.Loading
        val result = repository.signup(name, email, password)
        _signupFlow.value = result
    }

    fun logout() {
        repository.logout()
        _loginFlow.value = null
        _signupFlow.value = null
    }

}