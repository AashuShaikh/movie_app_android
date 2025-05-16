package com.aashushaikh.movieappcompose.movie.presentation.movie_detail_screen

import com.aashushaikh.movieappcompose.movie.presentation.model.MovieDetailUi

data class MovieDetailState(
    val movie: MovieDetailUi? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isBookmarked: Boolean = false
)
