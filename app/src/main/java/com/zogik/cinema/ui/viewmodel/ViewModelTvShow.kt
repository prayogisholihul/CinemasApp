package com.zogik.cinema.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.zogik.cinema.data.RepositoryTv
import com.zogik.cinema.data.room.local.TvEntity
import com.zogik.cinema.utils.Result

class ViewModelTvShow(private val repository: RepositoryTv) : ViewModel() {

    fun tvPagingData(): LiveData<Result<PagingData<TvEntity>>> = repository.pagingTv()
}
