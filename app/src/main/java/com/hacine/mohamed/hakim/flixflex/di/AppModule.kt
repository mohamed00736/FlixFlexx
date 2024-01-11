package com.hacine.mohamed.hakim.flixflex.di

import com.google.firebase.auth.FirebaseAuth
import com.hacine.mohamed.hakim.flixflex.presentation.SignUpScreen.AuthRepository
import com.hacine.mohamed.hakim.flixflex.presentation.SignUpScreen.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl
}