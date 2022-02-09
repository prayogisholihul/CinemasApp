package com.zogik.cinema.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zogik.cinema.data.Repository
import com.zogik.cinema.data.TvShowData
import com.zogik.cinema.utils.Result
import kotlinx.coroutines.launch

class ViewModelTvShow(private val repository: Repository) : ViewModel() {
    private val _tvShowData: MutableLiveData<Result<TvShowData?>> =
        MutableLiveData()
    var tvShowData: LiveData<Result<TvShowData?>> = _tvShowData

    fun getTvShow() = viewModelScope.launch {
        _tvShowData.value = Result.loading(null)
        try {
            val response = repository.getTvShow()
            _tvShowData.value = response
        } catch (e: Exception) {
            _tvShowData.value = Result.error(e.message.toString())
        }
    }
}
