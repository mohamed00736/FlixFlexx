package com.hacine.mohamed.hakim.flixflex.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.hacine.mohamed.hakim.flixflex.R
import com.hacine.mohamed.hakim.flixflex.data.models.Movie
import com.hacine.mohamed.hakim.flixflex.data.models.Tv
import com.hacine.mohamed.hakim.flixflex.ui.theme.CardColor
import com.hacine.mohamed.hakim.flixflex.ui.theme.TextColor
import com.hacine.mohamed.hakim.flixflex.data.network.Constants




@Composable
fun TrendingMovieListItem(movie: Movie?, onMovieSelected: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable { onMovieSelected() }
    ) {
        SubcomposeAsyncImage(
            model = Constants.Image_baseURL + movie?.posterPath,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            loading = {
                ThreeDotLoading()
            },
            error = {
                Image(
                    painter = painterResource(id = R.drawable.baseline_error_outline_24),
                    contentDescription = "",
                    modifier = Modifier.size(50.dp)
                )
            },
            modifier = Modifier
                .width(120.dp)
                .height(120.dp)
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(8.dp),
                    clip = true,
                    ambientColor = Color.Black.copy(1f)
                )
                .clip(shape = RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Displaying movie information on the right
        Column(
            modifier = Modifier
                .fillMaxWidth()
                //.weight(1f)
        ) {
            BasicText(
                text = movie?.name ?: "",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    fontStyle = MaterialTheme.typography.labelLarge.fontStyle
                ),
                overflow = TextOverflow.Visible,
                modifier = Modifier.wrapContentWidth()
            )
            Column(verticalArrangement = Arrangement.SpaceAround) {
                RatingBar(rating = (movie?.voteAverage?.div(2))?.toDouble() ?: 0.0, stars = 5)
                Spacer(modifier = Modifier.height(4.dp))
                BasicText(
                    text = "(${(movie?.voteAverage?.div(2))?.toDouble()})" ?: "",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.secondary,
                        fontStyle = MaterialTheme.typography.labelMedium.fontStyle
                    ),
                    overflow = TextOverflow.Visible,
                    modifier = Modifier.wrapContentWidth()
                )
            }

            Row(horizontalArrangement = Arrangement.Start) {
                BasicText(
                    text = "${movie?.releaseDate}" ?: "",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.secondary,
                        fontStyle = MaterialTheme.typography.labelMedium.fontStyle
                    ),
                    overflow = TextOverflow.Visible,
                    modifier = Modifier.wrapContentWidth()
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }
}

@Composable
fun TrendingSerieListItem(serie: Tv, onMovieSelected: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable { onMovieSelected() }
    ) {
        SubcomposeAsyncImage(
            model = Constants.Image_baseURL + serie.posterPath,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            loading = {
                ThreeDotLoading()
            },
            error = {
                Image(
                    painter = painterResource(id = R.drawable.baseline_error_outline_24),
                    contentDescription = "",
                    modifier = Modifier.size(50.dp)
                )
            },
            modifier = Modifier
                .width(120.dp)
                .height(120.dp)
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(8.dp),
                    clip = true,
                    ambientColor = Color.Black.copy(1f)
                )
                .clip(shape = RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Displaying movie information on the right
        Column(
            modifier = Modifier
                .fillMaxWidth()
            //.weight(1f)
        ) {
            BasicText(
                text = serie.name ?: "",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    fontStyle = MaterialTheme.typography.labelLarge.fontStyle
                ),
                overflow = TextOverflow.Visible,
                modifier = Modifier.wrapContentWidth()
            )
            Column(verticalArrangement = Arrangement.SpaceAround) {
                RatingBar(rating = (serie.voteAverage?.div(2))?.toDouble() ?: 0.0, stars = 5)
                Spacer(modifier = Modifier.height(4.dp))
                BasicText(
                    text = "(${(serie.voteAverage?.div(2))?.toDouble()})" ?: "",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.secondary,
                        fontStyle = MaterialTheme.typography.labelMedium.fontStyle
                    ),
                    overflow = TextOverflow.Visible,
                    modifier = Modifier.wrapContentWidth()
                )
            }

            Row(horizontalArrangement = Arrangement.Start) {
                BasicText(
                    text = "${serie.firstAirDate}" ?: "",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.secondary,
                        fontStyle = MaterialTheme.typography.labelMedium.fontStyle
                    ),
                    overflow = TextOverflow.Visible,
                    modifier = Modifier.wrapContentWidth()
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }
}

@Composable
fun ItemMovieCard(movie: Movie, onMovieSelected: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = { onMovieSelected() }),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = CardColor),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                Modifier
                    .height(150.dp)
                    .weight(6f)
            ) {
                SubcomposeAsyncImage(
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(16.dp)),
                    alignment = Alignment.Center,
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("${Constants.Image_baseURL}${movie.posterPath}")
                        .crossfade(true)
                        .build(),
                    contentDescription = "Image"
                ) {
                    val state = painter.state
                    if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            ThreeDotLoading()
                        }
                    } else {
                        SubcomposeAsyncImageContent(
                            modifier = Modifier.clip(RectangleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Box(
                    Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(Color.Black.copy(0.85f), shape = RoundedCornerShape(10.dp))
                        .padding(8.dp)
                ) {
                    Text(
                        text = "${movie.voteAverage}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier
                    .padding(5.dp)
                    .weight(5f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    movie.name?.let {
                        Text(
                            text = it,
                            color = TextColor,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    movie.releaseDate?.let {
                        Text(
                            text = it.take(4),
                            color = TextColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = movie.overview ?: "",
                        color = TextColor,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                }
            }
        }
    }
}


@Composable
fun ItemSerieCard(serie: Tv, onSeriesSelected: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = { onSeriesSelected() }),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = CardColor),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                Modifier
                    .height(150.dp)
                    .weight(6f)
            ) {
                SubcomposeAsyncImage(
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(16.dp)),
                    alignment = Alignment.Center,
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("${Constants.Image_baseURL}${serie.posterPath}")
                        .crossfade(true)
                        .build(),
                    contentDescription = "Image"
                ) {
                    val state = painter.state
                    if (state is AsyncImagePainter.State.Loading ) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            ThreeDotLoading()
                        }
                    } else if( state is AsyncImagePainter.State.Error){
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(text = "Error Loading Image")
                        }
                        //    error vie
                    }
                    else {
                        SubcomposeAsyncImageContent(
                            modifier = Modifier.clip(RectangleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Box(
                    Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(Color.Black.copy(0.85f), shape = RoundedCornerShape(10.dp))
                        .padding(8.dp)
                ) {
                    Text(
                        text = "${serie.voteAverage}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier
                    .padding(5.dp)
                    .weight(5f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    serie.name?.let {
                        Text(
                            text = it,
                            color = TextColor,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    serie.firstAirDate?.let {
                        Text(
                            text = it.take(4),
                            color = TextColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = serie.overview ?: "",
                        color = TextColor,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                }
            }
        }
    }
}


