package com.aashushaikh.movieappcompose.movie.presentation.movie_detail_screen

sealed interface MovieDetailAction {

    data class ToggleBookmark(val bookmark: Boolean): MovieDetailAction

}