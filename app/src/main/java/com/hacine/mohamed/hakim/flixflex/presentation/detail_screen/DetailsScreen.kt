package com.hacine.mohamed.hakim.flixflex.presentation.detail_screen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.hacine.mohamed.hakim.flixflex.R
import com.hacine.mohamed.hakim.flixflex.presentation.UiState

import com.hacine.mohamed.hakim.flixflex.ui.components.FailedView
import com.hacine.mohamed.hakim.flixflex.ui.components.LoadingView
import com.hacine.mohamed.hakim.flixflex.ui.components.RatingBar
import com.hacine.mohamed.hakim.flixflex.data.models.Movie
import com.hacine.mohamed.hakim.flixflex.data.models.Tv
import com.hacine.mohamed.hakim.flixflex.data.network.Constants
import com.hacine.mohamed.hakim.flixflex.ui.components.ThreeDotLoading
import com.hacine.mohamed.hakim.flixflex.ui.theme.CardColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    movieid: String? = null, seriesid: String? = null,
    onPlay: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel(),
    onBack: () -> Unit
) {


    LaunchedEffect(key1 = true){
        if (movieid != null){
            viewModel.getMovieById(movieid)
        }
        else{
            if (seriesid != null) {
                viewModel.getSeriesById(seriesid)
            }
        }

    }

    val uiState = viewModel.movieDetailuiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CardColor)
    ) {
        TopAppBar(colors = TopAppBarDefaults.topAppBarColors(containerColor = CardColor),
            title = { Text(text = "Detail") },
            navigationIcon = {
                IconButton(onClick = {
                    onBack()
                }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            modifier = Modifier.shadow(elevation = 16.dp)
        )



        when(uiState.value){
            is UiState.Loading ->{
                LoadingView()
            }
            is UiState.Success ->{
                val data = (uiState.value as? UiState.Success)?.data

                if (data is Movie ) {
                    //( (uiState.value as UiState.Success).data as Movie).let { movie ->
                        Log.d("MovieDetails", "MovieDetails: " +data.name)
                        Log.d("MovieDetails", "MovieDetails: " +data.posterPath)
                        MovieDetails(movie = data , onPlay= {
                            onPlay()
                        })

                  //  }
                } else if (data is Tv) {
                    val tvData = (uiState.value as UiState.Success).data as Tv
                    //( (uiState.value as UiState.Success).data as Tv).let { tv ->
                        Log.d("SerieDetails", "MovieDetailScreen: " + data.name)

                     SerieDetails(serie = tvData , onPlay = {
                         onPlay()
                     })

                    //}
                }

            }
            is UiState.Fail -> {
                (uiState.value as UiState.Fail).message?.let {
                    FailedView(
                        onRetry = {
                            if (movieid != null){
                                viewModel.getMovieById(movieid)
                            }
                            else{

                                viewModel.getSeriesById(seriesid!!)
                            }
                        },
                        retryable = true,
                        errorText = it
                    )
                }
            }

            else -> {}
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun MovieDetails(movie: Movie ,  onPlay: () -> Unit, ) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            DetailHeader(movie = movie , onPlay = {onPlay()})
        }
        item {
            movie.name?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }
        }
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RatingBar(rating = (movie.voteAverage?.div(2))?.toDouble() ?: 0.0, stars = 5,)
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Votes : ${ movie.voteCount.toString() }",
                    style = MaterialTheme.typography.bodyMedium,

                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 12.dp)
            ) {
                Icon(Icons.Default.DateRange, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                movie.releaseDate?.let { Text(text = it) }
            }
        }
        item {
            movie.overview?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun SerieDetails(serie: Tv,  onPlay: () -> Unit, ) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            DetailHeader(serie = serie , onPlay = {
                onPlay()
            })
        }
        item {
            Log.d("SerieDetails", "SerieDetails: " +serie.name)

            Text(
                text = serie.name,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(vertical = 12.dp) ,
            )
        }
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RatingBar(rating = (serie.voteAverage?.div(2))?.toDouble() ?: 0.0, stars = 5,)
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Votes : ${ serie.voteCount.toString() }",
                    style = MaterialTheme.typography.bodyMedium,

                    )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 12.dp)
            ) {
                Icon(Icons.Default.DateRange, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = serie.firstAirDate , modifier = Modifier
                    .padding(vertical = 12.dp)

                )
            }
        }
        item {
            serie.overview?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun DetailHeader(movie: Movie? = null , serie: Tv? = null, onPlay: () -> Unit,  ) {

    Log.d("DetailHeader", "DetailHeader: " + if (movie!=null ) movie.posterPath else serie?.posterPath)
    Log.d("DetailHeader", "DetailHeader: " + if (movie!=null ) movie.backdropPath else serie?.backdropPath)
    Log.d("DetailHeader", "DetailHeader: " + if (movie!=null ) movie.name else serie?.name)
    ConstraintLayout {
        val (banner, avatar , play) = createRefs()

        SubcomposeAsyncImage(
            model = Constants.Image_baseURL + if (movie!=null ) movie.backdropPath else serie?.backdropPath,
            contentDescription = null,
            loading = {
                ThreeDotLoading()
            },
            error = {
                Image(
                    painter = painterResource(id = R.drawable.baseline_error_outline_24),
                    contentDescription = ""
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(shape = MaterialTheme.shapes.medium)
                .constrainAs(banner) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            contentScale = ContentScale.FillBounds

        )

        SubcomposeAsyncImage(
            model = Constants.Image_baseURL + if (movie!=null ) movie.backdropPath else serie?.backdropPath,
            contentDescription = null,
            loading = {
                ThreeDotLoading()
            },
            error = {
                Image(
                    painter = painterResource(id = R.drawable.baseline_error_outline_24),
                    contentDescription = ""
                )
            },
            modifier = Modifier
                .size(100.dp)
                .clip(shape = RoundedCornerShape(16.dp))
                .border(
                    BorderStroke(
                        4.dp,
                        color = if (isSystemInDarkTheme()) Color.Black.copy(0.6f) else Color.White.copy(
                            0.2f
                        )
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
                .then(
                    Modifier.shadow(16.dp)
                )

                .constrainAs(avatar) {
                    top.linkTo(banner.bottom, margin = -75.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                }
                ,
            contentScale = ContentScale.Crop

        )

        IconButton(onClick = {
            onPlay()
        }, modifier = Modifier
            .size(50.dp)
            .clip(shape = CircleShape)
            .background(Color.Black)
            .constrainAs(play) {
                top.linkTo(banner.bottom, margin = -25.dp)
                end.linkTo(parent.end, margin = 16.dp)
            }) {
//            Surface(
//
//
//            ) {
                Icon(imageVector = Icons.Filled.PlayCircle, contentDescription = "", tint = Color.White)

            //}
        }


    }
}