package com.hacine.mohamed.hakim.flixflex.presentation.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.google.android.gms.common.util.VisibleForTesting
import com.hacine.mohamed.hakim.flixflex.data.models.Search
import com.hacine.mohamed.hakim.flixflex.data.repository.MoviesRepository
import com.hacine.mohamed.hakim.flixflex.data.repository.SearchRepository

import com.hacine.mohamed.hakim.flixflex.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.paging.cachedIn
import androidx.paging.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
) : ViewModel() {
    private var _multiSearch = mutableStateOf<Flow<PagingData<Search>>>(emptyFlow())
    val multiSearchState: State<Flow<PagingData<Search>>> = _multiSearch


    var searchParam = mutableStateOf("")
    var previousSearch = mutableStateOf("")


    init {
        searchParam.value = ""
    }

    fun searchRemoteMovie(includeAdult: Boolean) {
        viewModelScope.launch {
            if (searchParam.value.isNotEmpty()) {
                _multiSearch.value = searchRepository.multiSearch(
                    searchParams = searchParam.value,
                    includeAdult
                ).map { result ->
                    result.filter {
                        ((it.title != null || it.originalName != null || it.originalTitle != null) &&
                                (it.mediaType == "tv" || it.mediaType == "movie"))
                    }
                }.cachedIn(viewModelScope)
            }
        }
    }
}