package com.zogik.cinema.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.zogik.cinema.data.room.RoomDb
import com.zogik.cinema.data.room.favorite.MovieFavoriteEntity
import com.zogik.cinema.data.room.local.MovieEntity
import com.zogik.cinema.network.ApiInterface
import com.zogik.cinema.utils.AppExecutors
import com.zogik.cinema.utils.BaseDataSource
import com.zogik.cinema.utils.IdlingResource
import com.zogik.cinema.utils.NetworkBoundResource
import com.zogik.cinema.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepositoryMovie(
    private val api: ApiInterface,
    private val roomDatabase: RoomDb,
    private val appExecutors: AppExecutors
) : BaseDataSource() {
    suspend fun getDetailsMovie(movieId: Int) = getResult { api.getDetailsMovie(movieId) }
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

            override fun shouldFetch(data: PagingData<MovieEntity>?): Boolean =
                roomDatabase.movieDao().counts() == 0

            override fun createCall(): LiveData<Result<MovieData>> {
                IdlingResource.increment()
                val result = MutableLiveData<Result<MovieData>>()
                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        val response = api.getMoviePopular()
                        result.value = Result.success(response.body())
                    } catch (e: Exception) {
                        result.value = Result.error(e.message.toString())
                    } finally {
                        IdlingResource.decrement()
                    }
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
                roomDatabase.movieDao().insertAllMovies(arrayMovie)
            }
        }.asLiveData()

    suspend fun insertFavoriteMovie(movieData: MovieFavoriteEntity): Long =
        roomDatabase.movieFavoriteDao().insertFavorite(movieData)

    suspend fun deleteFavoriteMovie(movieData: MovieFavoriteEntity): Int =
        roomDatabase.movieFavoriteDao().delete(movieData)

    fun findFavoriteMovie(id: Int) = roomDatabase.movieFavoriteDao().findById(id)

    fun pagingFavMovie(): LiveData<PagingData<MovieFavoriteEntity>> =
        Pager(
            PagingConfig(
                pageSize = 4,
                initialLoadSize = 4,
                enablePlaceholders = false
            )
        ) {
            roomDatabase.movieFavoriteDao().getMovieFavorite()
        }.liveData
}
