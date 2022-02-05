package com.zogik.cinema.data

import com.zogik.cinema.network.ApiInterface

class Repository(
    private val api: ApiInterface
) {
    suspend fun getMovieList() = api.getMoviePopular()
    suspend fun getTvShow() = api.getTvPopular()
    suspend fun getDetailsMovie(movieId: Int) = api.getDetailsMovie(movieId)
    suspend fun getDetailsTv(tvId: Int) = api.getDetailsTv(tvId)
}
