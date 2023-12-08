package com.example.moviebuffs.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviebuffs.network.MovieApi
import com.example.moviebuffs.network.MoviePhoto
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface MovieUiState {
    data class Success(val photos: List<MoviePhoto>) : MovieUiState
    object Error : MovieUiState
    object Loading : MovieUiState
}

class MovieBuffsViewModel : ViewModel() {
    var movieUiState: MovieUiState by mutableStateOf(MovieUiState.Loading)
        private set

    init {
        getMoviePhotos()
    }

    fun getMoviePhotos() {
        viewModelScope.launch {
            movieUiState = try {
                MovieUiState.Success(MovieApi.retrofitService.getPhotos())
            } catch (e: IOException) {
                MovieUiState.Error
            }
        }
    }
}