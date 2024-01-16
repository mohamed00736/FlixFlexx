package com.hacine.mohamed.hakim.flixflex.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.hacine.mohamed.hakim.flixflex.data.network.AuthUiState
import com.hacine.mohamed.hakim.flixflex.utils.await

import javax.inject.Inject





class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun login(email: String, password: String): AuthUiState<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            AuthUiState.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            AuthUiState.Failure(e)
        }
    }

    override suspend fun signup(name: String, email: String, password: String): AuthUiState<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result?.user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())?.await()
            AuthUiState.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            AuthUiState.Failure(e)
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}
