package com.zogik.cinema.data.room.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {

    @Query("SELECT * FROM MovieEntity")
    fun getMovie(): PagingSource<Int, MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovies(movie: List<MovieEntity>)

    @Query("SELECT COUNT(*) from MovieEntity")
    fun counts(): Int
}
