package com.zogik.cinema.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.zogik.cinema.data.RepositoryMovie
import com.zogik.cinema.data.room.favorite.MovieFavoriteEntity

class ViewModelFavoriteMovie(private val repository: RepositoryMovie) : ViewModel() {

    fun getFavoriteMovie(): LiveData<PagingData<MovieFavoriteEntity>> = repository.pagingFavMovie()
}
