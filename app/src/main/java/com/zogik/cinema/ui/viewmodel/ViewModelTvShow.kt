package com.zogik.cinema.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zogik.cinema.data.Repository
import com.zogik.cinema.data.TvShowData
import com.zogik.cinema.utils.Resource
import kotlinx.coroutines.launch

class ViewModelTvShow(private val repository: Repository) : ViewModel() {
    private val _tvShowData: MutableLiveData<Resource<TvShowData?>> =
        MutableLiveData()
    var tvShowData: LiveData<Resource<TvShowData?>> = _tvShowData

    init {
        getTvShow()
    }

    private fun getTvShow() = viewModelScope.launch {
        _tvShowData.value = Resource.Loading()
        try {
            val response = repository.getTvShow()
            _tvShowData.value = Resource.Success(response.body())
        } catch (e: Exception) {
            _tvShowData.value = Resource.Error(e.message.toString())
        }
    }
}
