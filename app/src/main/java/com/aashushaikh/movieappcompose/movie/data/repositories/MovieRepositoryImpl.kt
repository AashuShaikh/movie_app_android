package com.aashushaikh.movieappcompose.movie.data.repositories

import com.aashushaikh.movieappcompose.movie.data.local.MovieDao
import com.aashushaikh.movieappcompose.movie.data.local.MovieDatabase
import com.aashushaikh.movieappcompose.movie.data.mappers.toMovieDetail
import com.aashushaikh.movieappcompose.movie.data.mappers.toMovieEntity
import com.aashushaikh.movieappcompose.movie.data.mappers.toMovieListItem
import com.aashushaikh.movieappcompose.movie.data.remote.MovieService
import com.aashushaikh.movieappcompose.movie.domain.models.Movie
import com.aashushaikh.movieappcompose.movie.domain.models.MovieDetail
import com.aashushaikh.movieappcompose.movie.domain.repositories.MovieRepository
import com.aashushaikh.movieappcompose.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val movieService: MovieService,
    private val movieDao: MovieDao
): MovieRepository {

    override suspend fun getMoviesList(): Flow<Resource<List<Movie>>> {
        return flow {
            try{
                emit(Resource.Loading(true))
                val cachedMoviesEntities = movieDao.getAllMovies()
                if(!cachedMoviesEntities.isNullOrEmpty()){
                    emit(Resource.Success(data = cachedMoviesEntities.map { it.toMovieListItem() }))
                }
                else{
                    val response = movieService.fetchMovies()
                    if(response.isSuccessful && response.body() != null){
                        val movies = response.body()
                        val movieList = movies!!.map { it.toMovieListItem() }
                        val movieEntities = movieList.map { it.toMovieEntity() }
                        movieDao.deleteMovies()
                        movieDao.insertMovies(movieEntities)
                        emit(Resource.Success(data = movieList))
                    }else{
                        emit(Resource.Error(message = "${response.code()}: ${response.message()}"))
                    }
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An error occurred"))
            }
        }
    }

    override suspend fun getMovieById(movieId: Int): Flow<Resource<MovieDetail>> {
        return flow {
            try {
                emit(Resource.Loading(true))
                val movie = movieDao.getMovieById(movieId)
                if(movie != null){
                    emit(Resource.Success(data = movie.toMovieDetail()))
                }else{
                    val response = movieService.fetchMovieById(movieId)
                    if(response.isSuccessful && response.body() != null){
                        val movie = response.body()
                        val movieDetail = movie!!.toMovieDetail()
                        emit(Resource.Success(data = movieDetail))
                    }else{
                    }
                }
            }catch (e: Exception){
                emit(Resource.Error(message = e.message ?: "An Error occurred"))
            }
        }
    }

}