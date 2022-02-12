package com.zogik.cinema.data.room.favorite

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieFavoriteDao {

    @Query("SELECT * FROM MovieFavoriteEntity")
    fun getMovieFavorite(): PagingSource<Int, MovieFavoriteEntity>

    @Query("SELECT * from MovieFavoriteEntity WHERE id=:id")
    fun findById(id: Int): LiveData<MovieFavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(movie: MovieFavoriteEntity): Long

    @Delete
    suspend fun delete(user: MovieFavoriteEntity): Int
}
