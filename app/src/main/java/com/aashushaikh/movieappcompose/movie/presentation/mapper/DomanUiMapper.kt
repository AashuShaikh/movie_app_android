package com.aashushaikh.movieappcompose.movie.presentation.mapper

import com.aashushaikh.movieappcompose.movie.domain.models.Movie
import com.aashushaikh.movieappcompose.movie.domain.models.MovieDetail
import com.aashushaikh.movieappcompose.movie.presentation.model.MovieDetailUi

fun Movie.toMovieDetailUi(): MovieDetailUi {
    return MovieDetailUi(
        title = title,
        overview = overview,
        posterPath = poster_path,
        voteAverage = vote_average,
        releaseDate = release_date,
        backdropPath = backdrop_path,
        adult = adult,
        originalLanguage = original_language,
        originalTitle = original_title,
        popularity = popularity,
        voteCount = vote_count,
        video = video,
        bookmared = bookmarked,

        // Movie-only → leave nullable fields as null
        genres = null,
        runtime = null,
        originCountry = null,
        spokenLanguages = null,
        movieId = null
    )
}

fun MovieDetail.toMovieDetailUi(): MovieDetailUi {
    return MovieDetailUi(
        title = title,
        overview = overview,
        posterPath = poster_path,
        voteAverage = vote_average,
        releaseDate = release_date,
        backdropPath = backdrop_path,
        adult = adult,
        originalLanguage = original_language,
        originalTitle = original_title,
        popularity = popularity,
        voteCount = vote_count,
        video = video,
        bookmared = bookmarked,

        // Extra fields from MovieDetail
        genres = genres,
        runtime = runtime,
        originCountry = origin_country,
        spokenLanguages = spoken_languages,
        movieId = movieId
    )
}
