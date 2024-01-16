package com.hacine.mohamed.hakim.flixflex.data.repository

import com.google.firebase.auth.FirebaseUser
import com.hacine.mohamed.hakim.flixflex.data.network.AuthUiState

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): AuthUiState<FirebaseUser>
    suspend fun signup(name: String, email: String, password: String): AuthUiState<FirebaseUser>
    fun logout()
}
