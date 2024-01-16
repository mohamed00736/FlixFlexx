package com.hacine.mohamed.hakim.flixflex.presentation


import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Tv
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.hacine.mohamed.hakim.flixflex.TabBarItem
import com.hacine.mohamed.hakim.flixflex.presentation.detail_screen.MovieDetailScreen
import com.hacine.mohamed.hakim.flixflex.presentation.login.LoginScreen
import com.hacine.mohamed.hakim.flixflex.presentation.movies_screen.MainScreen
import com.hacine.mohamed.hakim.flixflex.presentation.search.SearchScreen
import com.hacine.mohamed.hakim.flixflex.presentation.series_screen.SeriesScreen
import com.hacine.mohamed.hakim.flixflex.presentation.signup.AuthViewModel
import com.hacine.mohamed.hakim.flixflex.presentation.signup.SignUpScreen
import com.hacine.mohamed.hakim.flixflex.presentation.video_player_screen.VideoPlayerScreen
import com.hacine.mohamed.hakim.flixflex.ui.theme.CardColor


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavHost(
    viewModel: AuthViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onFinish: () -> Unit,
    startDestination: String = "login"
) {
    val moviesTab = TabBarItem(
        title = "movies",
        selectedIcon = Icons.Filled.Movie,
        unselectedIcon = Icons.Outlined.Movie
    )
    val seriesTab = TabBarItem(
        title = "series",
        selectedIcon = Icons.Filled.Tv,
        unselectedIcon = Icons.Outlined.Tv
    )

    val tabBarItems = listOf(moviesTab, seriesTab)

    Scaffold(bottomBar = {
        TabView(tabBarItems, navController)
    }
    ) {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = startDestination
        ) {

            composable(
                "${MainDestinations.Detail}?movieid={movieid}&seriesid={seriesid}",
                arguments = listOf(navArgument(name = "movieid") {
                    type = NavType.StringType
                    nullable = true
                }, navArgument(name = "seriesid") {
                    nullable = true
                    type = NavType.StringType
                })
            ) { from ->
                from.arguments?.getString("movieid").let { movieId ->

                    if (movieId != null) {
                        MovieDetailScreen(movieid = movieId , onPlay = {
                            navController.navigate("${MainDestinations.VideoPlayer}?movieid=${movieId}")
                        }) {
                            navController.navigateUp()
                        }
                    } else {
                        from.arguments?.getString("seriesid").let { serieid ->
                            MovieDetailScreen(seriesid = serieid, onPlay = {
                                navController.navigate("${MainDestinations.VideoPlayer}?seriesid=${serieid}")

                            }) {
                                navController.navigateUp()
                            }
                        }

                    }
                }
            }

            composable(MainDestinations.Movies) {
                BackHandler(enabled = true) {
                    onFinish()
                }
                MainScreen(onMovieSelected = { movieid ->
                    navController.navigate("${MainDestinations.Detail}?movieid=${movieid}")
                }, onSearchClick = {
                    navController.navigate(MainDestinations.Search)
                })
            }

            composable(MainDestinations.Series) {

                SeriesScreen (onSearchClick = {
                    navController.navigate(MainDestinations.Search)
                }){ seriesid ->
                    navController.navigate("${MainDestinations.Detail}?seriesid=${seriesid}")
                }
            }
            composable(MainDestinations.SignUp) {

                SignUpScreen( viewModel) {
                    navController.navigate(MainDestinations.Login) {
                        launchSingleTop = true
                    }
                }

            }
            composable("${MainDestinations.VideoPlayer}?movieid={movieid}&seriesid={seriesid}",
                arguments = listOf(navArgument(name = "movieid") {
                    type = NavType.StringType
                    nullable = true
                }, navArgument(name = "seriesid") {
                    nullable = true
                    type = NavType.StringType
                })) {from ->
                from.arguments?.getString("movieid").let { movieId ->
                    if (movieId != null) {
                        VideoPlayerScreen(movieid =movieId){
                        }
                    } else {
                        from.arguments?.getString("seriesid").let { serieid ->

                            VideoPlayerScreen(seriesid =serieid){
                                navController.navigateUp()
                            }
                        }
                    }
                }
            }

            composable(MainDestinations.Search) {

                SearchScreen(navigator = navController, onSelected = {
                    if (it.mediaType == "tv") {
                        navController.navigate("${MainDestinations.Detail}?seriesid=${it.id}")
                    } else {
                        navController.navigate("${MainDestinations.Detail}?movieid=${it.id}")
                    }

                })

            }

            composable(MainDestinations.Login) {
                BackHandler(enabled = true) {
                    onFinish()
                }
                LoginScreen(viewModel ,
                    onNavigateToMovies = {
                        navController.navigate(MainDestinations.Movies) {
                            launchSingleTop = true
                        }
                    },
                    onNavigateToSignUp = {
                        navController.navigate(MainDestinations.SignUp) {
                            launchSingleTop = true

                        }
                    }
                )


            }

        }
    }

}


@Composable
private fun TabView(tabBarItems: List<TabBarItem>, navController: NavController) {
    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = currentBackStackEntry?.destination?.route
    if (currentRoute.toString().contentEquals("movies") || currentRoute.toString()
            .contentEquals("series")
    ) {
        NavigationBar(modifier = Modifier.height(75.dp), containerColor = CardColor) {
            tabBarItems.forEachIndexed { index, tabBarItem ->
                NavigationBarItem(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        navController.navigate(tabBarItem.title)
                    },
                    icon = {
                        TabBarIconView(
                            isSelected = selectedTabIndex == index,
                            selectedIcon = tabBarItem.selectedIcon,
                            unselectedIcon = tabBarItem.unselectedIcon,
                            title = tabBarItem.title,

                            )
                    },
                    label = {
                        Text(tabBarItem.title)
                    })
            }
        }
    }
}


@Composable
private fun TabBarIconView(
    isSelected: Boolean,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    title: String,
) {
    Box {
        Icon(
            imageVector = if (isSelected) {
                selectedIcon
            } else {
                unselectedIcon
            },
            contentDescription = title
        )
    }
}
