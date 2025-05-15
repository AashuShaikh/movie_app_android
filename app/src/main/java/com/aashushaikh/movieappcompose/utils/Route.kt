package com.aashushaikh.movieappcompose.utils

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object HomeGraph: Route

    @Serializable
    data object AuthGraph: Route

    @Serializable
    data object Login: Route

    @Serializable
    data object Register: Route

    @Serializable
    data object MovieList: Route

    @Serializable
    data class MovieDetail(val id: Int): Route
}
