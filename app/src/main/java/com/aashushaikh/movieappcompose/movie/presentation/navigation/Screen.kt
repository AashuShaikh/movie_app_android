package com.aashushaikh.movieappcompose.movie.presentation.navigation

sealed class Screen(val route: String) {
    object MovieList: Screen("movie_list")
    object MovieDetail: Screen("movie_detail")
}