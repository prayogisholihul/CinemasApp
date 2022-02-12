package com.zogik.cinema.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.zogik.cinema.data.RepositoryMovie
import com.zogik.cinema.data.room.local.MovieEntity
import com.zogik.cinema.utils.Result

class ViewModelMovies(private val repository: RepositoryMovie) : ViewModel() {
    // paging
    fun moviesPagingData(): LiveData<Result<PagingData<MovieEntity>>> = repository.pagingMovie()
}
