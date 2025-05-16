package com.aashushaikh.movieappcompose.movie.domain.repositories

import com.aashushaikh.movieappcompose.movie.domain.models.Movie
import com.aashushaikh.movieappcompose.movie.domain.models.MovieDetail
import com.aashushaikh.movieappcompose.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getMoviesList(): Flow<Resource<List<Movie>>>
    suspend fun getMovieById(movieId: Int): Flow<Resource<MovieDetail>>
    suspend fun updateBookmark(movieId: Int)

}