package com.zogik.cinema.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zogik.cinema.data.room.favorite.MovieFavoriteDao
import com.zogik.cinema.data.room.favorite.MovieFavoriteEntity
import com.zogik.cinema.data.room.favorite.TvFavoriteDao
import com.zogik.cinema.data.room.favorite.TvFavoriteEntity
import com.zogik.cinema.data.room.local.MovieDao
import com.zogik.cinema.data.room.local.MovieEntity
import com.zogik.cinema.data.room.local.TvDao
import com.zogik.cinema.data.room.local.TvEntity

@Database(
    entities = [MovieEntity::class, MovieFavoriteEntity::class, TvEntity::class, TvFavoriteEntity::class],
    version = 1
)
abstract class RoomDb : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun movieFavoriteDao(): MovieFavoriteDao
    abstract fun tvDao(): TvDao
    abstract fun tvFavoriteDao(): TvFavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: RoomDb? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                RoomDb::class.java, "room_database"
            )
                .allowMainThreadQueries()
                .build()
    }
}
