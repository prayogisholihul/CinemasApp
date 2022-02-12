package com.zogik.cinema.data.room.favorite

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TvFavoriteDao {

    @Query("SELECT * FROM TvFavoriteEntity")
    fun getTvFavorite(): PagingSource<Int, TvFavoriteEntity>

    @Query("SELECT * from TvFavoriteEntity WHERE id=:id")
    fun findById(id: Int): LiveData<TvFavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(movie: TvFavoriteEntity): Long

    @Delete
    suspend fun delete(user: TvFavoriteEntity): Int
}
