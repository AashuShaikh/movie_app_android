package com.aashushaikh.movieappcompose.movie.data.remote

import com.aashushaikh.movieappcompose.movie.data.dto.MovieDetailResponseDto
import com.aashushaikh.movieappcompose.movie.data.dto.MovieResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {

    @GET("api/movies/popular")
    suspend fun fetchMovies(): Response<List<MovieResponseDto>>

    @GET("api/movies/movie/{id}")
    suspend fun fetchMovieById(
        @Path("id") movieId: Int
    ): Response<MovieDetailResponseDto>

}

// https://moviedatabase8.p.rapidapi.com/Random/20
// https://moviedatabase8.p.rapidapi.com/Search/Incep