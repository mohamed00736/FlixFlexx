package com.hacine.mohamed.hakim.flixflex.data.repository

import com.google.firebase.auth.FirebaseUser
import com.hacine.mohamed.hakim.flixflex.presentation.SignUpScreen.Resource

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): Resource<FirebaseUser>
    suspend fun signup(name: String, email: String, password: String): Resource<FirebaseUser>
    fun logout()
}