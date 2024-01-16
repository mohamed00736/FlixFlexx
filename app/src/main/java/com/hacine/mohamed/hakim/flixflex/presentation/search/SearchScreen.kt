package com.hacine.mohamed.hakim.flixflex.presentation.search

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.hilt.navigation.compose.hiltViewModel



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.hacine.mohamed.hakim.flixflex.data.models.Film
import com.hacine.mohamed.hakim.flixflex.ui.components.SearchBar
import com.hacine.mohamed.hakim.flixflex.ui.components.SearchResultItem
import com.hacine.mohamed.hakim.flixflex.ui.theme.AppOnPrimaryColor
import com.hacine.mohamed.hakim.flixflex.ui.theme.AppPrimaryColor
import com.hacine.mohamed.hakim.flixflex.ui.theme.AppPrimaryColor2
import com.hacine.mohamed.hakim.flixflex.ui.theme.CardColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navigator: NavController,
    searchViewModel: SearchViewModel = hiltViewModel(),
    onSelected: (navfilm: Film) -> Unit
) {
    val searchResult = searchViewModel.multiSearchState.value.collectAsLazyPagingItems()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CardColor)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Search for Movies & Series",
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            },
            modifier = Modifier.shadow(elevation = 16.dp),
            colors = TopAppBarDefaults.topAppBarColors(containerColor = CardColor),
            navigationIcon = {
                val focusManager = LocalFocusManager.current
                IconButton(onClick = {
                    focusManager.clearFocus()
                    navigator.navigateUp()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "",
                        modifier = Modifier)
                }

            }

        )
        SearchBar(
            autoFocus = true,
            onSearch = {
                searchViewModel.searchRemoteMovie(false)
            })

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            when (searchResult.loadState.refresh) {
                is LoadState.NotLoading -> {
                    items(count = searchResult.itemCount) { indexxx ->
                        indexxx.let {
                            val item = searchResult[it]!!
                            val focus = LocalFocusManager.current
                            SearchResultItem(
                                title = item!!.title,
                                mediaType = item.mediaType,
                                posterImage = "${item.posterPath}",

                                rating = (item.voteAverage ?: 0) as Double,
                                releaseYear = item.releaseDate,
                                onClick = {
                                    val navFilm = Film(
                                        adult = item.adult ?: false,
                                        backdropPath = item.backdropPath,
                                        posterPath = item.posterPath,
                                        genreIds = item.genreIds,

                                        mediaType = item.mediaType,
                                        id = item.id ?: 0,
                                        imdbId = item.imdbId,
                                        originalLanguage = item.originalLanguage ?: "",
                                        overview = item.overview ?: "",
                                        popularity = item.popularity ?: 0F.toDouble(),
                                        releaseDate = item.releaseDate ?: "",
                                        runtime = item.runtime,
                                        title = item.title ?: "",
                                        video = item.video ?: false,
                                        voteAverage = item.voteAverage ?: 0F.toDouble(),
                                        voteCount = item.voteCount ?: 0
                                    )
                                    focus.clearFocus()
                                    onSelected(navFilm)
                                })
                        }
                    }
                    if (searchResult.itemCount == 0) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 60.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No Match Found"
                                )
                            }

                        }
                    }
                }

                is LoadState.Loading -> item {
                    if (searchViewModel.searchParam.value.isNotEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                else -> item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 60.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No Match Found"
                        )
                    }
                }
            }
        }
    }
}