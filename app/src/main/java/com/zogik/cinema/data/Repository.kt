package com.zogik.cinema.data

import com.zogik.cinema.data.room.MovieEntity
import com.zogik.cinema.data.room.RoomDb
import com.zogik.cinema.network.ApiInterface
import com.zogik.cinema.utils.BaseDataSource
import com.zogik.cinema.utils.resultLiveData

class Repository(
    private val api: ApiInterface,
    private val roomDatabase: RoomDb
) : BaseDataSource() {
    suspend fun getMovieList() = getResult { api.getMoviePopular() }
    suspend fun getTvShow() = api.getTvPopular()
    suspend fun getDetailsMovie(movieId: Int) = api.getDetailsMovie(movieId)
    suspend fun getDetailsTv(tvId: Int) = api.getDetailsTv(tvId)
    fun pagingMovie() = resultLiveData(
        databaseQuery = { roomDatabase.movieDao().getMovie() },
        networkCall = { getResult { api.getMoviePopular() } },
        saveCallResult = { movieData ->
            val arrayMovie = ArrayList<MovieEntity>()
            movieData.results?.forEach { response ->
                val entity = MovieEntity(
                    id = response.id,
                    title = response.title,
                    releaseDate = response.releaseDate,
                    overview = response.overview,
                    voteAverage = response.voteAverage,
                    posterPath = response.posterPath
                )
                arrayMovie.add(entity)
            }
            roomDatabase.movieDao().insertAll(arrayMovie)
        }
    )
}
