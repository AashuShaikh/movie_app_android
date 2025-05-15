package com.aashushaikh.movieappcompose.movie.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aashushaikh.movieappcompose.movie.presentation.movie_detail_screen.MovieDetailScreen
import com.aashushaikh.movieappcompose.movie.presentation.movie_list_sreen.MovieListScreenRoot
//import com.aashushaikh.movieappcompose.movie.presentation.movie_list_sreen.MovieListScreen
import com.aashushaikh.movieappcompose.movie.presentation.movie_list_sreen.MovieListViewModel

@Composable
fun AppNavigation(viewModel: MovieListViewModel, modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.MovieList.route) {
        composable(route = Screen.MovieList.route) {
//            MovieListScreen(navController = navController, viewModel = viewModel, modifier = modifier)
            MovieListScreenRoot(
                onMovieClick = { movie ->
                    navController.navigate(Screen.MovieDetail.route + "?movie=${movie.id}")
                }
            )
        }
        composable(
            route = Screen.MovieDetail.route + "?movie={movie}",
            arguments = listOf(
                navArgument("movie") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
            val movieJson = it.arguments?.getString("movie")
            MovieDetailScreen(movieJson = movieJson, modifier = modifier)
        }
    }

}