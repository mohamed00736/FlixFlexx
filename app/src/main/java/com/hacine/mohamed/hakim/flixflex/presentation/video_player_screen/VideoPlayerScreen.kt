package com.hacine.mohamed.hakim.flixflex.presentation.video_player_screen

import android.util.Log
import android.widget.Toast

import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.hacine.mohamed.hakim.flixflex.presentation.UiState
import com.hacine.mohamed.hakim.flixflex.ui.components.FailedView
import com.hacine.mohamed.hakim.flixflex.ui.components.LoadingView
import com.hacine.mohamed.hakim.flixflex.data.models.MediaVideosList
import com.hacine.mohamed.hakim.flixflex.ui.theme.CardColor
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoPlayerScreen(
    movieid: String? = null, seriesid: String? = null,
    viewModel: VideoViewModel = hiltViewModel(),
    onBack: () -> Unit
) {


    LaunchedEffect(key1 = true) {
        if (movieid != null) {
            Log.d("movieid", "movieid: " + movieid)

            viewModel.getMovieVideos(movieid)
        } else {
            if (seriesid != null) {
                viewModel.getSeriesVideos(seriesid)
            }
        }
    }

    val uiState = viewModel.movieDetailuiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CardColor)
    ) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(containerColor = CardColor),
            title = { Text(text = "Play Trailer") },
            navigationIcon = {
                IconButton(onClick = {
                    onBack()
                }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            modifier = Modifier.shadow(elevation = 16.dp)
        )
        when (uiState.value) {
            is UiState.Loading -> {
                LoadingView()
            }

            is UiState.Success -> {
                val data = (uiState.value as? UiState.Success)?.data as MediaVideosList
                if (data.results.isEmpty()){
                Text(text = "No Trailer Videos to Play Sorry ")

                } else{
                    val videotrailer = data.results.filter {
                                   it.type == "Trailer"
                                || it.type == "Teaser"
                                || it.type == "Opening Credits"
                                || it.type == "Clip"
                                || it.type == "Featurette"
                    }[0]
                    AndroidView(factory = {
                        var view = YouTubePlayerView(it)
                        val fragment = view.addYouTubePlayerListener(
                            object : AbstractYouTubePlayerListener() {
                                override fun onReady(youTubePlayer: YouTubePlayer) {
                                    super.onReady(youTubePlayer)
                                    videotrailer.key?.let { videokey ->
                                        youTubePlayer.loadVideo(
                                            videokey,
                                            0f
                                        )
                                    }
                                }
                            }
                        )
                        view
                    })
                }

            }

            is UiState.Fail -> {
                (uiState.value as UiState.Fail).message?.let {
                    FailedView(
                        onRetry = {

                        },
                        retryable = true,
                        errorText = it
                    )
                }
            }

            else -> {}
        }
    }
}

