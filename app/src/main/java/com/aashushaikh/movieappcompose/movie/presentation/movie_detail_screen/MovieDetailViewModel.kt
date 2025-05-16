package com.aashushaikh.movieappcompose.movie.presentation.movie_detail_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aashushaikh.movieappcompose.movie.domain.repositories.MovieRepository
import com.aashushaikh.movieappcompose.movie.presentation.mapper.toMovieDetailUi
import com.aashushaikh.movieappcompose.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository
): ViewModel() {

    private var _movieDetailState = MutableStateFlow(MovieDetailState())
    val movieDetailState = _movieDetailState.asStateFlow()

    fun onAction(action: MovieDetailAction){
        when(action){
            is MovieDetailAction.ToggleBookmark -> {
                _movieDetailState.update {
                    it.copy(
                        movie = it.movie?.copy(bookmared = action.bookmark)
                    )
                }
                updateBookmark(movieDetailState.value.movie?.movieId?: -1)
            }
        }
    }

    private fun updateBookmark(movieId: Int) {
        viewModelScope.launch {
            repository.updateBookmark(movieId)
        }
    }

    fun getMovieById(movieId: Int){
        _movieDetailState.update {
            it.copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            repository.getMovieById(movieId).collect { result ->
                when(result){
                    is Resource.Loading -> {
                        _movieDetailState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                    is Resource.Error -> {
                        _movieDetailState.update {
                            it.copy(
                                error = result.message,
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Success -> {
                        _movieDetailState.update {
                            it.copy(
                                movie = result.data?.toMovieDetailUi(),
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

}