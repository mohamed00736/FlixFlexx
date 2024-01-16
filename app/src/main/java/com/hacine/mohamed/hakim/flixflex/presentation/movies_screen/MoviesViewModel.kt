package com.hacine.mohamed.hakim.flixflex.presentation.movies_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hacine.mohamed.hakim.flixflex.data.models.Movie
import com.hacine.mohamed.hakim.flixflex.data.repository.MoviesRepository

import com.hacine.mohamed.hakim.flixflex.presentation.UiState
import com.hacine.mohamed.hakim.flixflex.data.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val moviesRepository: MoviesRepository) :
    ViewModel() {

    private val _trendingMovieListuiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Idle)
    val trendingMovieListuiState: StateFlow<UiState> get() = _trendingMovieListuiState


    private var _moviesState: Flow<PagingData<Movie>>? = null
    val moviesState: Flow<PagingData<Movie>>? get() = _moviesState


    init {
        getMoviesPaged()
        getTrendingMovies()
    }


    private fun getMoviesPaged() {
        _moviesState = moviesRepository.getMoviesPaged().cachedIn(viewModelScope)
    }


    fun getTrendingMovies() {
        viewModelScope.launch {

            _trendingMovieListuiState.value = UiState.Loading
            when (val response = moviesRepository.getTrendingMovies()) {
                is Resource.Success -> {
                    _trendingMovieListuiState.value = UiState.Success(data = response.value)
                }

                is Resource.Failure -> {
                    _trendingMovieListuiState.value = UiState.Fail(message = response.message)
                }
            }

        }

    }

}