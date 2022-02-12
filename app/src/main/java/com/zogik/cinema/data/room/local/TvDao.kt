package com.zogik.cinema.data.room.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TvDao {
    @Query("SELECT * FROM TvEntity")
    fun getTvShow(): PagingSource<Int, TvEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTvShow(movie: List<TvEntity>)

    @Query("SELECT COUNT(*) from TvEntity")
    fun counts(): Int
}
