package com.aashushaikh.movieappcompose.movie.data.repositories

import com.aashushaikh.movieappcompose.movie.data.mappers.toMovieListItem
import com.aashushaikh.movieappcompose.movie.data.remote.MovieService
import com.aashushaikh.movieappcompose.movie.domain.models.Movie
import com.aashushaikh.movieappcompose.movie.domain.repositories.MovieRepository
import com.aashushaikh.movieappcompose.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val movieService: MovieService
): MovieRepository {

    override suspend fun getMoviesList(): Flow<Resource<List<Movie>>> {
        return flow {
            try{
                emit(Resource.Loading(true))
                val response = movieService.fetchMovies()
                if(response.isSuccessful && response.body() != null){
                    val movies = response.body()
                    emit(Resource.Success(data = movies!!.map { it.toMovieListItem() }))
                }else{
                    emit(Resource.Error(message = "${response.code()}: ${response.message()}"))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An error occurred"))
            }
        }
    }

}