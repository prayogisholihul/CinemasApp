package com.zogik.cinema.data.room

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.zogik.cinema.data.MovieData
import com.zogik.cinema.utils.Result

interface MovieDataSource {
    fun getAllMovie(): LiveData<Result<PagingData<MovieData>>>
}
