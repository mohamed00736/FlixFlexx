package com.hacine.mohamed.hakim.flixflex.presentation.series_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.hacine.mohamed.hakim.flixflex.presentation.UiState
import com.hacine.mohamed.hakim.flixflex.ui.components.FailedView
import com.hacine.mohamed.hakim.flixflex.data.models.TvList
import com.hacine.mohamed.hakim.flixflex.ui.components.LoadingView
import com.hacine.mohamed.hakim.flixflex.ui.components.ItemSerieCard
import com.hacine.mohamed.hakim.flixflex.ui.components.TrendingSerieListItem
import com.hacine.mohamed.hakim.flixflex.ui.theme.CardColor


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SeriesScreen(
    viewModel: SeriesViewModel = hiltViewModel(),
    onSearchClick: () -> Unit,
    onMovieSelected: (movieid: String) -> Unit
) {

    val trendingSeriesListState = viewModel.trendingSeriesListuiState.collectAsState()


    val lifecycleOwner = LocalLifecycleOwner.current
    val vseriesStatePagedFlow = remember(viewModel.pagedSeriesState, lifecycleOwner) {
        viewModel.pagedSeriesState?.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
    }

    val seriesStatePaged = vseriesStatePagedFlow?.collectAsLazyPagingItems()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CardColor)
    ) {
        TopAppBar(
            title = { Text(text = "Trending Series") },
            modifier = Modifier.shadow(elevation = 16.dp),
            colors = TopAppBarDefaults.topAppBarColors(containerColor = CardColor) ,
            actions = {
                IconButton(onClick = { onSearchClick() } ,  modifier = Modifier
                    .padding(horizontal = 8.dp)) {
                    Icon(
                        Icons.Filled.Search,
                        "",

                        )
                }

            }
        )

        LazyColumn(
            contentPadding = PaddingValues(
                top = 10.dp
            )) {

            item {

                Row(modifier = Modifier.padding(8.dp)){
                    Text(text = "Top 5 trending Series 🔥" ,
                        fontSize = 30.sp ,
                        fontWeight = FontWeight.ExtraBold ,)
                }
                when(trendingSeriesListState.value){

                    is UiState.Success -> {
                        ((trendingSeriesListState.value as UiState.Success).data as TvList).let { movieList->
                            LazyRow(
                                contentPadding = PaddingValues(vertical = 0.dp)
                            ) {
                                items(movieList.results.take(5)) { movie ->

                                    TrendingSerieListItem(serie = movie) {

                                        onMovieSelected(movie.id.toString())

                                    }

                                }
                            }
                        }
                    }
                    is UiState.Fail -> {
                        (trendingSeriesListState.value as UiState.Fail).message?.let {
                            FailedView(
                                onRetry = viewModel::getTrendingSeries,
                                retryable = true,
                                errorText = it
                            )
                        }
                    }

                    else -> Unit
                }
            }
            stickyHeader {
                Row(modifier = Modifier.background(shape = RoundedCornerShape(bottomStart = 16.dp , bottomEnd = 16.dp),
                   color =  CardColor).fillMaxWidth().height(50.dp)){
                    Text(text = "Discover More" ,
                        fontSize = 30.sp ,
                        fontWeight = FontWeight.ExtraBold , modifier = Modifier.padding(horizontal = 16.dp))
                }
            }

            seriesStatePaged?.let { it ->
                items(count = it.itemCount) { index ->
                    Box(modifier = Modifier.fillMaxWidth()) {
                        index.let {

                            val item = seriesStatePaged[it]!!

                            ItemSerieCard(serie = item){

                                onMovieSelected(item.id.toString())

                            }
                        }
                    }
                }
            }

            item {



                when (seriesStatePaged?.loadState?.refresh) {

                    is LoadState.Error -> {

                            FailedView(
                                errorText = "Modifier.fillParentMaxSize()",
                                retryable = true,
                                onRetry = {
                                    seriesStatePaged::retry.invoke()
                                }
                            )

                    }

                    is LoadState.Loading -> {


                            LoadingView()


                    }

                    is LoadState.NotLoading -> Unit
                    else -> {}
                }
                when (seriesStatePaged?.loadState?.append) {
                    is LoadState.Loading -> {


                        LoadingView()


                    }
                    is LoadState.Error -> {
                            FailedView(
                                errorText = "Retry please",
                                retryable = true,
                                onRetry = {
                                    seriesStatePaged::retry.invoke()
                                }
                            )
                    }


                    is LoadState.NotLoading -> {
                        if (seriesStatePaged.loadState.append.endOfPaginationReached && seriesStatePaged.itemCount == 0) {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Text(text = "No more data")
                                }
                        }
                    }
                    else -> {}
                }
            }
        }
    }
}