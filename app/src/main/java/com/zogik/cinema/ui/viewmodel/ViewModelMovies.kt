package com.zogik.cinema.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zogik.cinema.data.MovieData
import com.zogik.cinema.data.Repository
import com.zogik.cinema.utils.Resource
import kotlinx.coroutines.launch

class ViewModelMovies(private val repository: Repository) : ViewModel() {
    private val _moviesData: MutableLiveData<Resource<MovieData?>> =
        MutableLiveData()
    var moviesData: LiveData<Resource<MovieData?>> = _moviesData

    init {
        getMovies()
    }

    fun getMovies() = viewModelScope.launch {
        _moviesData.value = Resource.Loading()
        try {
            val response = repository.getMovieList()
            _moviesData.value = Resource.Success(response.body())
        } catch (e: Exception) {
            _moviesData.value = Resource.Error(e.message.toString())
        }
    }
}
