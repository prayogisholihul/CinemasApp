package com.zogik.cinema.ui.activity

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.skydoves.bundler.bundle
import com.skydoves.bundler.intentOf
import com.zogik.cinema.R
import com.zogik.cinema.data.MovieData
import com.zogik.cinema.data.TvShowData
import com.zogik.cinema.databinding.ActivityDetailBinding
import com.zogik.cinema.ui.viewmodel.DetailViewModel
import com.zogik.cinema.utils.IdlingResource
import com.zogik.cinema.utils.State
import com.zogik.cinema.utils.Utils
import com.zogik.cinema.utils.Utils.viewGone
import com.zogik.cinema.utils.Utils.viewVisible
import org.koin.androidx.viewmodel.ext.android.viewModel

class ActivityDetail : AppCompatActivity(R.layout.activity_detail) {
    private val dataMovie: MovieData.ResultsItem? by bundle(PASS_DATA_MOVIE)
    private val dataTv: TvShowData.ResultsItem? by bundle(PASS_DATA_TV)
    private val binding by viewBinding<ActivityDetailBinding>()
    private val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObserve()
        setButton()
        setVM()
    }

    private fun setButton() {
        binding.fabShare.setOnClickListener {
            Utils.showToast(this, "Tombol Share Ditekan")
        }
    }

    private fun setVM() {
        if (dataMovie != null)
            dataMovie?.id?.let { viewModel.getDetailMovie(it) }
        if (dataTv != null)
            dataTv?.id?.let { viewModel.getDetailTv(it) }
    }

    private fun setObserve() {
        val imageLink = "https://image.tmdb.org/t/p/w500"

        viewModel.dataDetailMovie.observe(
            this
        ) {
            when (it) {
                is State.Loading -> {
                    viewVisible(binding.loading)
                }
                is State.Success -> {
                    IdlingResource.increment()
                    Handler(Looper.getMainLooper()).postDelayed({
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

                        IdlingResource.decrement()
                    }, 3000)
                }
                is State.Error -> {
                    viewGone(binding.loading)
                    Utils.showToast(this, getString(R.string.toast_text))
                }
            }
        }

        viewModel.dataDetailTv.observe(this) {
            when (it) {
                is State.Loading -> {
                    viewVisible(binding.loading)
                }
                is State.Success -> {
                    IdlingResource.increment()
                    Handler(Looper.getMainLooper()).postDelayed({
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

                        IdlingResource.decrement()
                    }, 4000)
                }
                is State.Error -> {
                    viewGone(binding.loading)
                    Utils.showToast(this, getString(R.string.toast_text))
                }
            }
        }
    }

    companion object {
        const val PASS_DATA_MOVIE = "MOVIE"
        const val PASS_DATA_TV = "TV"

        fun Context.passToDetailMovie(data: MovieData.ResultsItem) = intentOf<ActivityDetail> {
            +(PASS_DATA_MOVIE to data)
            startActivity(this@passToDetailMovie)
        }

        fun Context.passToDetailTv(data: TvShowData.ResultsItem) = intentOf<ActivityDetail> {
            +(PASS_DATA_TV to data)
            startActivity(this@passToDetailTv)
        }
    }
}
