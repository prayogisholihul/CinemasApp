package com.zogik.cinema.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zogik.cinema.data.DetailMovieData
import com.zogik.cinema.data.DetailTvData
import com.zogik.cinema.data.Repository
import com.zogik.cinema.utils.Result
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: Repository) : ViewModel() {
    private val _dataDetailMovie: MutableLiveData<Result<DetailMovieData?>> = MutableLiveData()
    var dataDetailMovie: LiveData<Result<DetailMovieData?>> = _dataDetailMovie

    fun getDetailMovie(movieId: Int) = viewModelScope.launch {
        _dataDetailMovie.value = Result.loading()
        try {
            val response = repository.getDetailsMovie(movieId)
            _dataDetailMovie.value = response
        } catch (e: Exception) {
            _dataDetailMovie.value = Result.error(e.message.toString())
        }
    }

    private val _dataDetailTv: MutableLiveData<Result<DetailTvData?>> = MutableLiveData()
    val dataDetailTv: LiveData<Result<DetailTvData?>> = _dataDetailTv

    fun getDetailTv(tvId: Int) = viewModelScope.launch {
        _dataDetailTv.value = Result.loading()
        try {
            val response = repository.getDetailsTv(tvId)
            _dataDetailTv.value = response
        } catch (e: Exception) {
            _dataDetailTv.value = Result.error(e.message.toString())
        }
    }
}
