package com.aashushaikh.movieappcompose.movie.presentation.movie_list_sreen

import com.aashushaikh.movieappcompose.movie.domain.models.Movie

sealed interface MovieListAction {

    data class OnSearchQueryChanged(val query: String): MovieListAction
    data class OnMovieClicked(val movie: Movie): MovieListAction
    data class OnTabSelected(val index: Int): MovieListAction

}