package com.zogik.cinema.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class TvShowData(

    @field:SerializedName("page")
    val page: Int? = null,

    @field:SerializedName("total_pages")
    val totalPages: Int? = null,

    @field:SerializedName("results")
    val results: List<ResultsItem>? = null,

    @field:SerializedName("total_results")
    val totalResults: Int? = null
) {
    @Parcelize
    data class ResultsItem(

        @field:SerializedName("first_air_date")
        val firstAirDate: String? = null,

        @field:SerializedName("overview")
        val overview: String? = null,

        @field:SerializedName("poster_path")
        val posterPath: String? = null,

        @field:SerializedName("id")
        val id: Int? = null,

        @field:SerializedName("vote_average")
        val voteAverage: Double? = null,

        @field:SerializedName("name")
        val name: String? = null
    ) : Parcelable
}
