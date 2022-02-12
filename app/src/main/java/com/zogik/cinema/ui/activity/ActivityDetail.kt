package com.zogik.cinema.ui.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.skydoves.bundler.bundle
import com.skydoves.bundler.intentOf
import com.zogik.cinema.R
import com.zogik.cinema.data.DetailMovieData
import com.zogik.cinema.data.DetailTvData
import com.zogik.cinema.data.room.favorite.MovieFavoriteEntity
import com.zogik.cinema.data.room.favorite.TvFavoriteEntity
import com.zogik.cinema.data.room.local.MovieEntity
import com.zogik.cinema.data.room.local.TvEntity
import com.zogik.cinema.databinding.ActivityDetailBinding
import com.zogik.cinema.ui.viewmodel.DetailViewModel
import com.zogik.cinema.utils.IdlingResource
import com.zogik.cinema.utils.Result
import com.zogik.cinema.utils.Utils.showToast
import com.zogik.cinema.utils.Utils.viewGone
import com.zogik.cinema.utils.Utils.viewVisible
import org.koin.androidx.viewmodel.ext.android.viewModel

class ActivityDetail : AppCompatActivity(R.layout.activity_detail) {
    private val dataMovie: MovieEntity? by bundle(PASS_DATA_MOVIE)
    private val dataTv: TvEntity? by bundle(PASS_DATA_TV)
    private val dataFavMovie: MovieFavoriteEntity? by bundle(PASS_MOVIE_FAV)
    private val dataFavTv: TvFavoriteEntity? by bundle(PASS_TV_FAV)
    private val binding by viewBinding<ActivityDetailBinding>()
    private val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObserve()
        setVM()
    }

    private fun setVM() {
        if (dataMovie != null) {
            IdlingResource.increment()
            dataMovie?.id?.let { viewModel.getDetailMovie(it) }
            IdlingResource.decrement()
        }
        if (dataTv != null) {
            IdlingResource.increment()
            dataTv?.id?.let { viewModel.getDetailTv(it) }
            IdlingResource.decrement()
        }
        if (dataFavMovie != null) {
            IdlingResource.increment()
            dataFavMovie?.id?.let { viewModel.getDetailMovie(it) }
            IdlingResource.decrement()
        }
        if (dataFavTv != null) {
            IdlingResource.increment()
            dataFavTv?.id?.let { viewModel.getDetailTv(it) }
            IdlingResource.decrement()
        }
    }

    private fun setObserve() {
        val imageLink = "https://image.tmdb.org/t/p/w500"

        viewModel.dataDetailMovie.observe(
            this
        ) {
            when (it.status) {
                Result.Status.LOADING -> {
                    viewVisible(binding.loading)
                }
                Result.Status.SUCCESS -> {
                    viewGone(binding.loading)
                    binding.contentTitle.text = it.data?.title
                    binding.contentTitle.isSelected = true
                    binding.contentDate.text = it.data?.releaseDate
                    binding.contentRating.text = it.data?.voteAverage?.toString()
                    binding.tvOverview.text = it.data?.overview

                    Glide.with(this)
                        .load(imageLink + it.data?.posterPath)
                        .centerCrop()
                        .placeholder(android.R.drawable.ic_menu_gallery)
                        .into(binding.ivPict)
                    setFavoriteMovie(it.data)
                    IdlingResource.decrement()
                }
                Result.Status.ERROR -> {
                    viewGone(binding.loading)
                    showToast(this, getString(R.string.toast_text))
                    IdlingResource.decrement()
                }
            }
        }

        viewModel.dataDetailTv.observe(this) {
            when (it.status) {
                Result.Status.LOADING -> {
                    viewVisible(binding.loading)
                }
                Result.Status.SUCCESS -> {
                    viewGone(binding.loading)
                    binding.contentTitle.text = it.data?.name
                    binding.contentTitle.isSelected = true
                    binding.contentDate.text = it.data?.firstAirDate
                    binding.contentRating.text = it.data?.voteAverage?.toString()
                    binding.tvOverview.text = it.data?.overview

                    Glide.with(this)
                        .load(imageLink + it.data?.posterPath)
                        .centerCrop()
                        .placeholder(android.R.drawable.ic_menu_gallery)
                        .into(binding.ivPict)
                    setFavoriteTv(it.data)
                    IdlingResource.decrement()
                }
                Result.Status.ERROR -> {
                    viewGone(binding.loading)
                    showToast(this, getString(R.string.toast_text))
                    IdlingResource.decrement()
                }
            }
        }
    }

    private fun setFavoriteMovie(data: DetailMovieData?) {
        binding.fabFavorite.setOnClickListener {
            viewModel.insertToFavoriteMovie(data)
        }

        data?.id?.let {
            viewModel.getFavoriteMovieById(it).observe(this) { favMovieData ->
                if (data.id == favMovieData?.id) {
                    binding.apply {
                        fabFavorite.setImageResource(R.drawable.ic_favorited)
                        fabFavorite.setOnClickListener {
                            viewModel.deleteFavoriteMovie(favMovieData)
                            showToast(this@ActivityDetail, "Deleted From Favorite")
                            IdlingResource.decrement()
                        }
                    }
                } else {
                    binding.apply {
                        fabFavorite.setImageResource(R.drawable.ic_favorite)
                        fabFavorite.setOnClickListener {
                            viewModel.insertToFavoriteMovie(data)
                            showToast(this@ActivityDetail, "Added To Favorite")
                            IdlingResource.decrement()
                        }
                    }
                }
            }
        }
    }

    private fun setFavoriteTv(data: DetailTvData?) {
        binding.fabFavorite.setOnClickListener {
            viewModel.insertToFavoriteTv(data)
        }

        data?.id?.let {
            viewModel.getFavoriteTvById(it).observe(this) { favTvData ->
                if (data.id == favTvData?.id) {
                    binding.apply {
                        fabFavorite.setImageResource(R.drawable.ic_favorited)
                        fabFavorite.setOnClickListener {
                            viewModel.deleteFavoriteTv(favTvData)
                            showToast(this@ActivityDetail, "Deleted From Favorite")
                            IdlingResource.decrement()
                        }
                    }
                } else {
                    binding.apply {
                        fabFavorite.setImageResource(R.drawable.ic_favorite)
                        fabFavorite.setOnClickListener {
                            viewModel.insertToFavoriteTv(data)
                            showToast(this@ActivityDetail, "Added To Favorite")
                            IdlingResource.decrement()
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val PASS_DATA_MOVIE = "MOVIE"
        const val PASS_DATA_TV = "TV"
        const val PASS_MOVIE_FAV = "MOVFAV"
        const val PASS_TV_FAV = "TVFAV"

        fun Context.passToDetailMovie(data: MovieEntity) = intentOf<ActivityDetail> {
            +(PASS_DATA_MOVIE to data)
            startActivity(this@passToDetailMovie)
        }

        fun Context.passToDetailTv(data: TvEntity) = intentOf<ActivityDetail> {
            +(PASS_DATA_TV to data)
            startActivity(this@passToDetailTv)
        }

        fun Context.passFromFavMovie(data: MovieFavoriteEntity) = intentOf<ActivityDetail> {
            +(PASS_MOVIE_FAV to data)
            startActivity(this@passFromFavMovie)
        }

        fun Context.passFromFavTv(data: TvFavoriteEntity) = intentOf<ActivityDetail> {
            +(PASS_TV_FAV to data)
            startActivity(this@passFromFavTv)
        }
    }
}
