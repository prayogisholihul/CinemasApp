package com.zogik.cinema.data.room.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class TvEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int?,

    @ColumnInfo(name = "overview")
    val overview: String?,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "poster_path")
    val posterPath: String?,

    @ColumnInfo(name = "release_date")
    val firstAirDate: String?,

    @ColumnInfo(name = "vote_average")
    val voteAverage: Double?
) : Parcelable