package com.aashushaikh.movieappcompose.movie.presentation.model

data class MovieDetailUi(
    val title: String,
    val overview: String,
    val posterPath: String,
    val voteAverage: Double,
    val releaseDate: String,
    val backdropPath: String,
    val adult: Boolean,
    val originalLanguage: String,
    val originalTitle: String,
    val popularity: Double,
    val voteCount: Int,
    val video: Boolean,
    val bookmared: Boolean,

    // Nullable for fields only in MovieDetail
    val genres: List<String>? = null,
    val runtime: Int? = null,
    val originCountry: List<String>? = null,
    val spokenLanguages: List<String>? = null,
    val movieId: Int? = null,
)

