package com.hacine.mohamed.hakim.flixflex.presentation.movies_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.hacine.mohamed.hakim.flixflex.presentation.UiState
import com.hacine.mohamed.hakim.flixflex.ui.components.FailedView
import com.hacine.mohamed.hakim.flixflex.data.models.MovieList
import com.hacine.mohamed.hakim.flixflex.ui.components.ItemMovieCard
import com.hacine.mohamed.hakim.flixflex.ui.components.LoadingView
import com.hacine.mohamed.hakim.flixflex.ui.components.ThreeDotLoading
import com.hacine.mohamed.hakim.flixflex.ui.components.TrendingMovieListItem
import com.hacine.mohamed.hakim.flixflex.ui.theme.CardColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MoviesViewModel = hiltViewModel(),
    onSearchClick: () -> Unit,
    onMovieSelected: (movieid: String) -> Unit,

) {

    val movieListState = viewModel.movieListuiState.collectAsState()
    val trendingMovieListState = viewModel.trendingMovieListuiState.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current
    val vmoviesStatePagedFlow = remember(viewModel.moviesState, lifecycleOwner) {
        viewModel.moviesState?.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
    }

    val moviesStatePaged = vmoviesStatePagedFlow?.collectAsLazyPagingItems()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CardColor)
    ) {
        TopAppBar(
            title = { Text(text = "Trending movies") },

            modifier = Modifier.shadow(elevation = 16.dp), actions = {
                IconButton(onClick = { onSearchClick() } ,  modifier = Modifier
                    .padding(horizontal = 8.dp)) {
                    Icon(
                        Icons.Filled.Search,
                        "",

                    )
                }

            } ,
            colors = TopAppBarDefaults.topAppBarColors(containerColor = CardColor))


        LazyColumn(

            contentPadding = PaddingValues(
            top = 10.dp
        )) {

                item {
                    when(trendingMovieListState.value){
                        is UiState.Loading -> {
                            ThreeDotLoading()
                         //   LoadingView()
                        }
                        is UiState.Success -> {
                            ((trendingMovieListState.value as UiState.Success).data as MovieList).let { movieList->
                                LazyRow(
                                    contentPadding = PaddingValues(vertical = 0.dp)
                                ) {
                                    items(movieList.results) { movie ->

                                        TrendingMovieListItem(movie = movie) {

                                            onMovieSelected(movie.id.toString())

                                        }

                                    }
                                }
                            }
                        }
                        is UiState.Fail -> {
                            (trendingMovieListState.value as UiState.Fail).message?.let {
                                FailedView(
                                    onRetry = viewModel::getTrendingMovies,
                                    retryable = true,
                                    errorText = it
                                )
                            }
                        }

                        else -> Unit
                    }
                }

            moviesStatePaged?.let { it ->
                items(count = it.itemCount) { index ->
                    Box(modifier = Modifier.fillMaxWidth()) {
                        index.let {

                            val item = moviesStatePaged[it]!!

                            ItemMovieCard(movie = item){

                               onMovieSelected(item.id.toString())

                            }
                        }
                    }
                }
            }

            when (moviesStatePaged?.loadState?.refresh) {

                is LoadState.Error -> {
                    item {
                        FailedView(
                            errorText = "Modifier.fillParentMaxSize()",
                            retryable = true,
                            onRetry = {
                                moviesStatePaged::retry.invoke()
                            }
                        )
                    }
                }

                is LoadState.NotLoading -> Unit

                is LoadState.Loading -> {
                    item {
                        LoadingView()
                    }
                }
                else -> {}
            }
            when (moviesStatePaged?.loadState?.append) {
                is LoadState.Error -> {
                    item {
                        FailedView(
                            errorText = "Modifier.fillParentMaxSize()",
                            retryable = true,
                            onRetry = {
                                moviesStatePaged::retry.invoke()
                            }
                        )
                    }
                }

                is LoadState.NotLoading -> {
                    if (moviesStatePaged.loadState.append.endOfPaginationReached && moviesStatePaged.itemCount == 0) {
                        item {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                               // modifier = Modifier.fillParentMaxSize()
                            ) {
                                Text(text = "No more data")

                            }
                        }
                    }
                }


                else -> {}
            }
        }

    }


}








