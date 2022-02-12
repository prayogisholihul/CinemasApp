package com.zogik.cinema.utils

import com.zogik.cinema.data.DetailMovieData
import com.zogik.cinema.data.DetailTvData
import com.zogik.cinema.data.room.favorite.MovieFavoriteEntity
import com.zogik.cinema.data.room.favorite.TvFavoriteEntity

fun dataDetailMovie(): DetailMovieData = DetailMovieData(
    id = 634649,
    overview = "mantap",
    title = "Eternal",
    posterPath = "Poster",
    releaseDate = "22-02-2022",
    voteAverage = 9.9
)

fun dataFavMovie(): MovieFavoriteEntity = MovieFavoriteEntity(
    id = dataDetailMovie().id,
    overview = dataDetailMovie().overview,
    title = dataDetailMovie().title,
    posterPath = dataDetailMovie().posterPath,
    releaseDate = dataDetailMovie().releaseDate,
    voteAverage = dataDetailMovie().voteAverage
)

fun dataDetailTv(): DetailTvData = DetailTvData(
    id = 110492,
    overview = "mantap",
    name = "Hawkeye",
    posterPath = "Poster",
    firstAirDate = "30-02-2022",
    voteAverage = 10.0
)

fun dataFavTv(): TvFavoriteEntity = TvFavoriteEntity(
    id = dataDetailTv().id,
    overview = dataDetailTv().overview,
    name = dataDetailTv().name,
    posterPath = dataDetailTv().posterPath,
    firstAirDate = dataDetailTv().firstAirDate,
    voteAverage = dataDetailTv().voteAverage
)
