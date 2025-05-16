package com.aashushaikh.movieappcompose.movie.data.repositories

import com.aashushaikh.movieappcompose.movie.data.local.bookmarks.BookmarkDao
import com.aashushaikh.movieappcompose.movie.data.local.bookmarks.BookmarkEntity
import com.aashushaikh.movieappcompose.movie.data.local.movie.MovieDao
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
    private val movieDao: MovieDao,
    private val bookmarkDao: BookmarkDao
): MovieRepository {

    override suspend fun getMoviesList(): Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading(true))

            val cachedMoviesEntities = movieDao.getAllMovies()
            val cachedBookmarks = bookmarkDao.getAllBookmarks()
            val bookmarkedIds = cachedBookmarks.map { it.movieId }.toSet()

            if (cachedMoviesEntities.isNotEmpty()) {
                val movieList = cachedMoviesEntities.map { entity ->
                    entity.toMovieListItem().copy(bookmarked = bookmarkedIds.contains(entity.movieId))
                }
                emit(Resource.Success(data = movieList))
            } else {
                val response = movieService.fetchMovies()
                if (response.isSuccessful) {
                    val moviesDto = response.body().orEmpty()
                    val movieList = moviesDto.map { dto ->
                        dto.toMovieListItem().copy(bookmarked = bookmarkedIds.contains(dto.id))
                    }
                    val movieEntities = movieList.map { it.toMovieEntity() }

                    movieDao.deleteMovies()
                    movieDao.insertMovies(movieEntities)

                    emit(Resource.Success(data = movieList))
                } else {
                    emit(Resource.Error(message = "${response.code()}: ${response.message()}"))
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "An unknown error occurred"))
        }
    }


    override suspend fun getMovieById(movieId: Int): Flow<Resource<MovieDetail>> {
        return flow {
            try {
                emit(Resource.Loading(true))
                val movie = movieDao.getMovieById(movieId)
                val cachedBookmarks = bookmarkDao.getAllBookmarks()
                val bookmarkedIds = cachedBookmarks.map { it.movieId }.toSet()
                val isBookmarked = bookmarkedIds.contains(movieId)
                if(movie != null){
                    emit(Resource.Success(data = movie.toMovieDetail().copy(bookmarked = isBookmarked)))
                }else{
                    val response = movieService.fetchMovieById(movieId)
                    if(response.isSuccessful && response.body() != null){
                        val movieResponse = response.body()
                        val movieDetail = movieResponse!!.toMovieDetail().copy(bookmarked = isBookmarked)
                        emit(Resource.Success(data = movieDetail))
                    }else{
                        emit(Resource.Error(message = "${response.code()}: ${response.message()}"))
                    }
                }
            }catch (e: Exception){
                emit(Resource.Error(message = e.message ?: "An Error occurred"))
            }
        }
    }

    override suspend fun updateBookmark(movieId: Int) {
        val bookmarkEntity = BookmarkEntity(movieId = movieId)
        val cachedBookmarks = bookmarkDao.getAllBookmarks()
        val bookmarkedIds = cachedBookmarks.map { it.movieId }.toSet()
        if(bookmarkedIds.contains(movieId)){
            bookmarkDao.deleteBookmark(bookmarkEntity)
        }else{
            bookmarkDao.addBookmark(bookmarkEntity)
        }
    }

}