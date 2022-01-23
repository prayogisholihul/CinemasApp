package com.zogik.cinema.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zogik.cinema.data.Repository
import com.zogik.cinema.data.TvShowData
import com.zogik.cinema.utils.State
import kotlinx.coroutines.launch

class ViewModelTvShow(private val repository: Repository) : ViewModel() {
    private val _tvShowData: MutableLiveData<State<TvShowData?>> =
        MutableLiveData()
    var tvShowData: LiveData<State<TvShowData?>> = _tvShowData

    init {
        getTvShow()
    }

    private fun getTvShow() = viewModelScope.launch {
        _tvShowData.value = State.Loading()
        try {
            val response = repository.getTvShow()
            _tvShowData.value = State.Success(response.body())
        } catch (e: Exception) {
            _tvShowData.value = State.Error(e.message.toString())
        }
    }
}
