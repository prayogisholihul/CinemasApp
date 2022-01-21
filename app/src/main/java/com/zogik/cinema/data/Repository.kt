package com.zogik.cinema.data

import com.zogik.cinema.network.ApiInterface

class Repository(
    private val api: ApiInterface
) {
    suspend fun getMovieList() = api.getMoviePopular()
    suspend fun getTvShow() = api.getTvPopular()
}
