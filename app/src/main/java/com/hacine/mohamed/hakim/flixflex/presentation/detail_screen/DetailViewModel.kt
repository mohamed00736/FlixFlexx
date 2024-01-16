package com.hacine.mohamed.hakim.flixflex.presentation.detail_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hacine.mohamed.hakim.flixflex.data.repository.MoviesRepository
import com.hacine.mohamed.hakim.flixflex.presentation.UiState
import com.hacine.mohamed.hakim.flixflex.data.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val moviesRepository: MoviesRepository) :
    ViewModel() {


    private val _movieDetailuiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Idle)
    val movieDetailuiState: StateFlow<UiState> get() = _movieDetailuiState

    private val _serieDetailuiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Idle)
    val serieDetailuiState: StateFlow<UiState> get() = _serieDetailuiState

    fun getMovieById(movieid: String) {
        viewModelScope.launch {
            Log.d("getMovieById", "getMovieById: ")
            _movieDetailuiState.value = UiState.Loading
            when (val response = moviesRepository.getMovieById(movieid)) {
                is Resource.Success -> {
                    _movieDetailuiState.value = UiState.Success(data = response.value)
                    Log.d("getMovieById", "getMovieById: " + response.value)
                }

                is Resource.Failure -> {
                    _movieDetailuiState.value = UiState.Fail(message = response.message)
                }
            }
        }
    }

    fun getSeriesById(serieid: String) {
        viewModelScope.launch {
            Log.d("getSeriesById", "getSeriesById: ")
            _movieDetailuiState.value = UiState.Loading
            when (val response = moviesRepository.getSeriesById(serieid)) {
                is Resource.Success -> {
                    _movieDetailuiState.value = UiState.Success(data = response.value)
                    Log.d("getSeriesById", "getSeriesById: " + response.value)
                }

                is Resource.Failure -> {
                    _movieDetailuiState.value = UiState.Fail(message = response.message)
                }
            }
        }
    }
}