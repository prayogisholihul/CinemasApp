package com.zogik.cinema.network

import com.zogik.cinema.data.DetailMovieData
import com.zogik.cinema.data.DetailTvData
import com.zogik.cinema.data.MovieData
import com.zogik.cinema.data.TvShowData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("3/movie/popular")
    suspend fun getMoviePopular(): Response<MovieData>

    @GET("3/tv/popular")
    suspend fun getTvPopular(): Response<TvShowData>

    @GET("3/movie/{movie_id}")
    suspend fun getDetailsMovie(@Path("movie_id") movieId: Int): Response<DetailMovieData>

    @GET("3/tv/{tv_id}")
    suspend fun getDetailsTv(@Path("tv_id") tvId: Int): Response<DetailTvData>
}
