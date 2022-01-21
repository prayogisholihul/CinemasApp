package com.zogik.cinema.network

import com.zogik.cinema.data.MovieData
import com.zogik.cinema.data.TvShowData
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("3/movie/popular")
    suspend fun getMoviePopular(): Response<MovieData>

    @GET("3/tv/popular")
    suspend fun getTvPopular(): Response<TvShowData>
}
