package com.aashushaikh.movieappcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.aashushaikh.movieappcompose.auth.presentation.AuthViewModel
import com.aashushaikh.movieappcompose.auth.presentation.LoginScreenRoot
import com.aashushaikh.movieappcompose.auth.presentation.RegisterScreenRoot
import com.aashushaikh.movieappcompose.movie.presentation.movie_detail_screen.MovieDetailScreenRoot
import com.aashushaikh.movieappcompose.movie.presentation.movie_list_sreen.MovieListScreenRoot
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
                        startDestination = if (state.value.isAuthenticated) {
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
                                LoginScreenRoot(authViewModel = authViewModel, onGotoRegisterClick = {
                                    navController.navigate(Route.Register)
                                })
                            }
                            composable<Route.Register> {
                                RegisterScreenRoot(authViewModel = authViewModel, onGotoLoginClick = {
                                    navController.navigate(Route.Login)
                                })
                            }
                        }

                        // HomeGraph navigation
                        navigation<Route.HomeGraph>(
                            startDestination = Route.MovieList
                        ) {
                            composable<Route.MovieList> {
                                MovieListScreenRoot(
                                    onMovieClick = { movie ->
                                        navController.navigate(Route.MovieDetail(movie.movieId))
                                    },
                                    modifier = Modifier.padding(innerPadding)
                                )
                            }

                            composable<Route.MovieDetail> { backStackEntry ->
                                val args = backStackEntry.toRoute<Route.MovieDetail>()
                                args.let {
                                    val movieId = it.id
                                    Log.d("MovieDetail", "MovieId: $movieId")
                                    MovieDetailScreenRoot(movieId = movieId)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

