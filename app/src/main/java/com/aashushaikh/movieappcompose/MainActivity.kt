package com.aashushaikh.movieappcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.aashushaikh.movieappcompose.auth.presentation.AuthViewModel
import com.aashushaikh.movieappcompose.auth.presentation.LoginScreen
import com.aashushaikh.movieappcompose.movie.presentation.movie_detail_screen.MovieDetailScreen
import com.aashushaikh.movieappcompose.movie.presentation.movie_list_sreen.MovieListScreenRoot
import com.aashushaikh.movieappcompose.movie.presentation.navigation.AppNavigation
import com.aashushaikh.movieappcompose.movie.presentation.navigation.Screen
import com.aashushaikh.movieappcompose.ui.theme.MovieAppComposeTheme
import com.aashushaikh.movieappcompose.utils.Route
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieAppComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val authViewModel: AuthViewModel = hiltViewModel()
                    val state = authViewModel.authState.collectAsState()

                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = if (state.value.isAuthenticated == true) {
                            Route.HomeGraph
                        } else {
                            Route.AuthGraph
                        }
                    ) {
                        // AuthGraph navigation
                        navigation<Route.AuthGraph>(
                            startDestination = Route.Login
                        ) {
                            composable<Route.Login> {
                                LoginScreen(authViewModel = authViewModel)
                            }
                        }

                        // HomeGraph navigation
                        navigation<Route.HomeGraph>(
                            startDestination = Route.MovieList
                        ) {
                            composable<Route.MovieList> {
                                MovieListScreenRoot(
                                    onMovieClick = { movie ->
                                        navController.navigate(Route.MovieDetail(movie.id))
                                    },
                                    modifier = Modifier.padding(innerPadding)
                                )
                            }

                            composable<Route.MovieDetail> { backStackEntry ->
                                val args = backStackEntry.toRoute<Route.MovieDetail>()
                                args.let {
                                    val movieId = it.id
                                    Log.d("MovieDetail", "MovieId: $movieId")
                            //                                    MovieDetailScreen(id = movieId)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

