package com.zogik.cinema.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zogik.cinema.data.DetailMovieData
import com.zogik.cinema.data.DetailTvData
import com.zogik.cinema.data.RepositoryMovie
import com.zogik.cinema.data.RepositoryTv
import com.zogik.cinema.data.room.favorite.MovieFavoriteEntity
import com.zogik.cinema.data.room.favorite.TvFavoriteEntity
import com.zogik.cinema.utils.Result
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repositoryMovie: RepositoryMovie,
    private val repositoryTv: RepositoryTv
) : ViewModel() {
    private val _dataDetailMovie: MutableLiveData<Result<DetailMovieData?>> = MutableLiveData()
    var dataDetailMovie: LiveData<Result<DetailMovieData?>> = _dataDetailMovie

    fun getDetailMovie(movieId: Int) = viewModelScope.launch {
        _dataDetailMovie.value = Result.loading()
        try {
            val response = repositoryMovie.getDetailsMovie(movieId)
            _dataDetailMovie.value = response
        } catch (e: Exception) {
            _dataDetailMovie.value = Result.error(e.message.toString())
        }
    }

    private val _dataDetailTv: MutableLiveData<Result<DetailTvData?>> = MutableLiveData()
    val dataDetailTv: LiveData<Result<DetailTvData?>> = _dataDetailTv

    fun getDetailTv(tvId: Int) = viewModelScope.launch {
        _dataDetailTv.value = Result.loading()
        try {
            val response = repositoryTv.getDetailsTv(tvId)
            _dataDetailTv.value = response
        } catch (e: Exception) {
            _dataDetailTv.value = Result.error(e.message.toString())
        }
    }

    // FAV MOVIE

    fun insertToFavoriteMovie(movieData: DetailMovieData?) = viewModelScope.launch {
        repositoryMovie.insertFavoriteMovie(
            MovieFavoriteEntity(
                id = movieData?.id,
                overview = movieData?.overview,
                title = movieData?.title,
                posterPath = movieData?.posterPath,
                releaseDate = movieData?.releaseDate,
                voteAverage = movieData?.voteAverage
            )
        )
    }

    fun getFavoriteMovieById(id: Int) = repositoryMovie.findFavoriteMovie(id)

    fun deleteFavoriteMovie(movieData: MovieFavoriteEntity) = viewModelScope.launch {
        repositoryMovie.deleteFavoriteMovie(movieData)
    }

    // FAV TV

    fun insertToFavoriteTv(tvData: DetailTvData?) = viewModelScope.launch {
        repositoryTv.insertFavoriteTv(
            TvFavoriteEntity(
                id = tvData?.id,
                overview = tvData?.overview,
                name = tvData?.name,
                posterPath = tvData?.posterPath,
                firstAirDate = tvData?.firstAirDate,
                voteAverage = tvData?.voteAverage
            )
        )
    }

    fun getFavoriteTvById(id: Int) = repositoryTv.findFavoriteTv(id)

    fun deleteFavoriteTv(tvData: TvFavoriteEntity) = viewModelScope.launch {
        repositoryTv.deleteFavoriteTv(tvData)
    }
}
