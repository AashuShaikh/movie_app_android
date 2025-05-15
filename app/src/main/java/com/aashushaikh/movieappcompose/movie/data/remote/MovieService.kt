package com.aashushaikh.movieappcompose.movie.data.remote

import com.aashushaikh.movieappcompose.movie.data.dto.MovieResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface MovieService {

    @GET("api/movies/popular")
    suspend fun fetchMovies(): Response<List<MovieResponseDto>>

}

// https://moviedatabase8.p.rapidapi.com/Random/20
// https://moviedatabase8.p.rapidapi.com/Search/Incep