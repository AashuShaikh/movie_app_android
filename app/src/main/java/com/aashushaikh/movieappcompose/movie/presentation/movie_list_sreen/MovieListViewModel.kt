package com.aashushaikh.movieappcompose.movie.presentation.movie_list_sreen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aashushaikh.movieappcompose.movie.domain.repositories.MovieRepository
import com.aashushaikh.movieappcompose.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MovieRepository
): ViewModel() {

    private var _movieListState = MutableStateFlow(MovieListState())
    val movieListState = _movieListState.asStateFlow()
//        .onStart { getMoviesList() }
//        .stateIn(
//            viewModelScope,
//            SharingStarted.WhileSubscribed(5000L),
//            MovieListState()
//        )

    init {
        getMoviesList()
    }

    fun onAction(action: MovieListAction){
        when(action){
            is MovieListAction.OnMovieClicked -> {}
            is MovieListAction.OnSearchQueryChanged -> {}
            is MovieListAction.OnTabSelected -> {}
        }
    }

    private fun getMoviesList(){
        _movieListState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            repository.getMoviesList().collect{result ->
                when(result){
                    is Resource.Loading -> {
                        _movieListState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        _movieListState.update {
                            it.copy(searchResults = result.data!!, isLoading = false)
                        }
                    }
                    is Resource.Error -> {
                        _movieListState.update {
                            it.copy(error = result.message, isLoading = false)
                        }
                    }
                }
            }
        }
    }
}