package com.zogik.cinema.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zogik.cinema.data.MovieData

class DetailViewModel : ViewModel() {
    private val _dataDetail: MutableLiveData<MovieData.ResultsItem?> = MutableLiveData()
    private val dataDetail: LiveData<MovieData.ResultsItem?> = _dataDetail

    fun getDataDetail() = dataDetail

    fun setDataDetail(data: MovieData.ResultsItem?) {
        _dataDetail.value = data
    }
}
