package com.zogik.cinema.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.zogik.cinema.data.Repository
import com.zogik.cinema.data.room.MovieEntity
import com.zogik.cinema.utils.Result

class ViewModelMovies(repository: Repository) : ViewModel() {
    // paging
    val moviesPagingData: LiveData<Result<List<MovieEntity>>> = repository.pagingMovie()
}
