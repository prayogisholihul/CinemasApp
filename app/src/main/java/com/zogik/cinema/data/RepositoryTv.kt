package com.zogik.cinema.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.zogik.cinema.data.room.RoomDb
import com.zogik.cinema.data.room.favorite.TvFavoriteEntity
import com.zogik.cinema.data.room.local.TvEntity
import com.zogik.cinema.network.ApiInterface
import com.zogik.cinema.utils.AppExecutors
import com.zogik.cinema.utils.BaseDataSource
import com.zogik.cinema.utils.IdlingResource
import com.zogik.cinema.utils.NetworkBoundResource
import com.zogik.cinema.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepositoryTv(
    private val api: ApiInterface,
    private val roomDatabase: RoomDb,
    private val appExecutors: AppExecutors
) : BaseDataSource() {
    suspend fun getDetailsTv(tvId: Int) = getResult { api.getDetailsTv(tvId) }
    fun pagingTv() =
        object : NetworkBoundResource<PagingData<TvEntity>, TvShowData>(appExecutors) {
            override fun loadFromDB(): LiveData<PagingData<TvEntity>> {
                return Pager(
                    PagingConfig(
                        pageSize = 4,
                        initialLoadSize = 4,
                        enablePlaceholders = false
                    )
                ) {
                    roomDatabase.tvDao().getTvShow()
                }.liveData
            }

            override fun shouldFetch(data: PagingData<TvEntity>?): Boolean =
                roomDatabase.tvDao().counts() == 0

            override fun createCall(): LiveData<Result<TvShowData>> {
                IdlingResource.increment()
                val result = MutableLiveData<Result<TvShowData>>()
                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        val response = api.getTvPopular()
                        result.value = Result.success(response.body())
                    } catch (e: Exception) {
                        result.value = Result.error(e.message.toString())
                    } finally {
                        IdlingResource.decrement()
                    }
                }
                return result
            }

            override fun saveCallResult(data: TvShowData) {
                val arrayMovie = ArrayList<TvEntity>()
                data.results?.forEach { response ->
                    val entity = TvEntity(
                        id = response.id,
                        name = response.name,
                        firstAirDate = response.firstAirDate,
                        overview = response.overview,
                        voteAverage = response.voteAverage,
                        posterPath = response.posterPath
                    )
                    arrayMovie.add(entity)
                }
                roomDatabase.tvDao().insertAllTvShow(arrayMovie)
            }
        }.asLiveData()

    fun findFavoriteTv(id: Int) = roomDatabase.tvFavoriteDao().findById(id)

    suspend fun insertFavoriteTv(data: TvFavoriteEntity): Long =
        roomDatabase.tvFavoriteDao().insertFavorite(data)

    suspend fun deleteFavoriteTv(data: TvFavoriteEntity): Int =
        roomDatabase.tvFavoriteDao().delete(data)

    fun pagingFavTv(): LiveData<PagingData<TvFavoriteEntity>> =
        Pager(
            PagingConfig(
                pageSize = 4,
                initialLoadSize = 4,
                enablePlaceholders = false
            )
        ) {
            roomDatabase.tvFavoriteDao().getTvFavorite()
        }.liveData
}
