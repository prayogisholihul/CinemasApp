package com.zogik.cinema.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.zogik.cinema.data.RepositoryTv
import com.zogik.cinema.data.room.favorite.TvFavoriteEntity

class ViewModelFavoriteTv(private val repositoryTv: RepositoryTv) : ViewModel() {

    fun getFavoriteTv(): LiveData<PagingData<TvFavoriteEntity>> = repositoryTv.pagingFavTv()
}
