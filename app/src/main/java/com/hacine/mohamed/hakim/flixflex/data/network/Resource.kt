package com.hacine.mohamed.hakim.flixflex.data.network


import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody

sealed class Resource<out T> {
    data class Success<out T>(val value: T) : Resource<T>()

    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int,
        val message: String,
        val errorBody: ResponseBody?,

        ) : Resource<Nothing>()
}

sealed class AuthUiState<out R> {
    data class Success<out R>(val result: R) : AuthUiState<R>()
    data class Failure(val exception: Exception) : AuthUiState<Nothing>()
    object Loading : AuthUiState<Nothing>()
}

data class ErrorApiResponse(
    @SerializedName("status_code") val status_code: Int?,
    @SerializedName("status_message") val status_message: String?,
    @SerializedName("success") val success: Boolean?,
)