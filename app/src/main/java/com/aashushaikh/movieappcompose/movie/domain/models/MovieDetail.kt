package com.aashushaikh.movieappcompose.movie.domain.models

data class MovieDetail(
    val adult: Boolean,
    val backdrop_path: String,
    val genres: List<String>,
    val movieId: Int,
    val origin_country: List<String>,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val runtime: Int,
    val spoken_languages: List<String>,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)