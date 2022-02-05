package com.zogik.cinema.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zogik.cinema.data.DetailMovieData
import com.zogik.cinema.data.DetailTvData
import com.zogik.cinema.data.Repository
import com.zogik.cinema.utils.State
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: Repository) : ViewModel() {
    private val _dataDetailMovie: MutableLiveData<State<DetailMovieData?>> = MutableLiveData()
    var dataDetailMovie: LiveData<State<DetailMovieData?>> = _dataDetailMovie

    fun getDetailMovie(movieId: Int) = viewModelScope.launch {
        _dataDetailMovie.value = State.Loading()
        try {
            val response = repository.getDetailsMovie(movieId)
            _dataDetailMovie.value = State.Success(response.body())
        } catch (e: Exception) {
            _dataDetailMovie.value = State.Error(e.message.toString())
        }
    }

    private val _dataDetailTv: MutableLiveData<State<DetailTvData?>> = MutableLiveData()
    val dataDetailTv: LiveData<State<DetailTvData?>> = _dataDetailTv

    fun getDetailTv(tvId: Int) = viewModelScope.launch {
        _dataDetailTv.value = State.Loading()
        try {
            val response = repository.getDetailsTv(tvId)
            _dataDetailTv.value = State.Success(response.body())
        } catch (e: Exception) {
            _dataDetailTv.value = State.Error(e.message.toString())
        }
    }
}
