package com.zogik.cinema.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zogik.cinema.data.MovieData
import com.zogik.cinema.data.TvShowData

class DetailViewModel : ViewModel() {
    private val _dataDetailMovie: MutableLiveData<MovieData.ResultsItem?> = MutableLiveData()
    var dataDetailMovie: LiveData<MovieData.ResultsItem?> = _dataDetailMovie

    @JvmName("getDataDetailMovie1")
    fun getDataDetailMovie() = dataDetailMovie

    fun setDataDetailMovie(data: MovieData.ResultsItem?) {
        _dataDetailMovie.value = data
    }

    private val _dataDetailTv: MutableLiveData<TvShowData.ResultsItem?> = MutableLiveData()
    val dataDetailTv: LiveData<TvShowData.ResultsItem?> = _dataDetailTv

    @JvmName("getDataDetailTv1")
    fun getDataDetailTv() = dataDetailTv

    fun setDataDetailTv(data: TvShowData.ResultsItem?) {
        _dataDetailTv.value = data
    }
}
