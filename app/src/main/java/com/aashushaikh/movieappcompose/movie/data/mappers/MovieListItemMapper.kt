package com.aashushaikh.movieappcompose.movie.data.mappers

import com.aashushaikh.movieappcompose.movie.data.dto.MovieResponseDto
import com.aashushaikh.movieappcompose.movie.domain.models.Movie

fun MovieResponseDto.toMovieListItem(): Movie {
//    val releaseDate = AppConstants.yearFormatter(this.release_date)
    return Movie(
        adult = adult,
        backdrop_path = backdrop_path,
        genre_ids = genre_ids,
        id = id,
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