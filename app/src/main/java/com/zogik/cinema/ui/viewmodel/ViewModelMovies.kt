package com.zogik.cinema.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zogik.cinema.data.MovieData
import com.zogik.cinema.data.Repository
import com.zogik.cinema.utils.State
import kotlinx.coroutines.launch

class ViewModelMovies(private val repository: Repository) : ViewModel() {
    private val _moviesData: MutableLiveData<State<MovieData?>> =
        MutableLiveData()
    var moviesData: LiveData<State<MovieData?>> = _moviesData

    fun getMovies() = viewModelScope.launch {
        _moviesData.value = State.Loading()
        try {
            val response = repository.getMovieList()
            _moviesData.value = State.Success(response.body())
        } catch (e: Exception) {
            _moviesData.value = State.Error(e.message.toString())
        }
    }
}
