package com.hacine.mohamed.hakim.flixflex.presentation.series_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hacine.mohamed.hakim.flixflex.data.models.Tv
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
class SeriesViewModel @Inject constructor(private val moviesRepository: MoviesRepository) :
    ViewModel() {



    private val _trendingSeriesListuiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Idle)
    val trendingSeriesListuiState: StateFlow<UiState> get() = _trendingSeriesListuiState

    private var _pagedSeriesState: Flow<PagingData<Tv>>? = null
    val pagedSeriesState: Flow<PagingData<Tv>>? get() = _pagedSeriesState

    init {
        getTrendingSeries()
        getSeriesPaged()
    }


    private fun getSeriesPaged() {
        _pagedSeriesState = moviesRepository.getSeriesPaged().cachedIn(viewModelScope)
    }



    fun getTrendingSeries() {
        viewModelScope.launch {

            _trendingSeriesListuiState.value = UiState.Loading
            when (val response = moviesRepository.getTrendingSeries()) {
                is Resource.Success -> {

                    _trendingSeriesListuiState.value = UiState.Success(data = response.value)
                }

                is Resource.Failure -> {
                    _trendingSeriesListuiState.value = UiState.Fail(message = response.message)
                }
            }

        }

    }

}