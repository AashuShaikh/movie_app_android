package com.aashushaikh.movieappcompose.movie.presentation.movie_list_sreen

import com.aashushaikh.movieappcompose.movie.domain.models.Movie

data class MovieListState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val searchResults: List<Movie> = emptyList(),
    val bookmarkedMovies: List<Movie> = emptyList(),
    val selectedTabIndex: Int = 0
)