package com.zogik.cinema.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.zogik.cinema.data.room.MovieEntity
import com.zogik.cinema.data.room.RoomDb
import com.zogik.cinema.network.ApiInterface
import com.zogik.cinema.utils.AppExecutors
import com.zogik.cinema.utils.BaseDataSource
import com.zogik.cinema.utils.NetworkBoundResource
import com.zogik.cinema.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Repository(
    private val api: ApiInterface,
    private val roomDatabase: RoomDb,
    private val appExecutors: AppExecutors
) : BaseDataSource() {
    suspend fun getMovieList() = getResult { api.getMoviePopular() }
    suspend fun getTvShow() = getResult { api.getTvPopular() }
    suspend fun getDetailsMovie(movieId: Int) = getResult { api.getDetailsMovie(movieId) }
    suspend fun getDetailsTv(tvId: Int) = getResult { api.getDetailsTv(tvId) }
    fun pagingMovie() =
        object : NetworkBoundResource<PagingData<MovieEntity>, MovieData>(appExecutors) {
            override fun loadFromDB(): LiveData<PagingData<MovieEntity>> {
                return Pager(
                    PagingConfig(
                        pageSize = 4,
                        initialLoadSize = 4,
                        enablePlaceholders = false
                    )
                ) {
                    roomDatabase.movieDao().getMovie()
                }.liveData
            }

            override fun shouldFetch(data: PagingData<MovieEntity>?): Boolean = data == null

            override fun createCall(): LiveData<Result<MovieData>> {
                val result = MutableLiveData<Result<MovieData>>()
                CoroutineScope(Dispatchers.Main).launch {
                    val response = api.getMoviePopular()
                    result.value = Result.success(response.body())
                }
                return result
            }

            override fun saveCallResult(data: MovieData) {
                val arrayMovie = ArrayList<MovieEntity>()
                data.results?.forEach { response ->
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
        }.asLiveData()
}
