package com.aashushaikh.movieappcompose.movie.data.mappers

import com.aashushaikh.movieappcompose.movie.data.dto.MovieDetailResponseDto
import com.aashushaikh.movieappcompose.movie.data.dto.MovieResponseDto
import com.aashushaikh.movieappcompose.movie.data.local.movie.MovieEntity
import com.aashushaikh.movieappcompose.movie.domain.models.Movie
import com.aashushaikh.movieappcompose.movie.domain.models.MovieDetail

fun MovieResponseDto.toMovieListItem(): Movie {
//    val releaseDate = AppConstants.yearFormatter(this.release_date)
    return Movie(
        adult = adult,
        backdrop_path = backdrop_path,
        genre_ids = genre_ids,
        movieId = id,
        original_language = original_language,
        original_title = original_title,
        overview = overview,
        popularity = popularity,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
        video = video,
        vote_average = vote_average,
        vote_count = vote_count
    )
}

fun MovieEntity.toMovieListItem(): Movie {
    return Movie(
        adult = adult,
        backdrop_path = backdrop_path,
        genre_ids = genre_ids,
        movieId = movieId,
        original_language = original_language,
        original_title = original_title,
        overview = overview,
        popularity = popularity,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
        video = video,
        vote_average = vote_average,
        vote_count = vote_count
    )
}

fun Movie.toMovieEntity(): MovieEntity {
    return MovieEntity(
        adult = adult,
        backdrop_path = backdrop_path,
        genre_ids = genre_ids,
        movieId = movieId,
        original_language = original_language,
        original_title = original_title,
        overview = overview,
        popularity = popularity,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
        video = video,
        vote_average = vote_average,
        vote_count = vote_count
    )
}

fun MovieDetailResponseDto.toMovieDetail(): MovieDetail {
    return MovieDetail(
        adult = adult,
        backdrop_path = backdrop_path,
        genres = genres,
        movieId = id,
        origin_country = origin_country,
        original_language = original_language,
        original_title = original_title,
        overview = overview,
        popularity = popularity,
        poster_path = poster_path,
        release_date = release_date,
        spoken_languages = spoken_languages,
        title = title,
        video = video,
        vote_average = vote_average,
        vote_count = vote_count,
        runtime = runtime
    )
}

fun MovieEntity.toMovieDetail(): MovieDetail {
    return MovieDetail(
        adult = adult,
        backdrop_path = backdrop_path,
        genres = emptyList(),
        movieId = movieId,
        origin_country = emptyList(),
        original_language = original_language,
        original_title = original_title,
        overview = overview,
        popularity = popularity,
        poster_path = poster_path,
        release_date = release_date,
        spoken_languages = emptyList(),
        title = title,
        video = video,
        vote_average = vote_average,
        vote_count = vote_count,
        runtime = 0
    )
}