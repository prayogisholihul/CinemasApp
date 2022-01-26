package com.zogik.cinema.ui.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.skydoves.bundler.bundle
import com.skydoves.bundler.intentOf
import com.zogik.cinema.R
import com.zogik.cinema.data.MovieData
import com.zogik.cinema.data.TvShowData
import com.zogik.cinema.databinding.ActivityDetailBinding
import com.zogik.cinema.ui.viewmodel.DetailViewModel
import com.zogik.cinema.utils.Utils

class ActivityDetail : AppCompatActivity(R.layout.activity_detail) {
    private val dataMovie: MovieData.ResultsItem? by bundle(PASS_DATA_MOVIE)
    private val dataTv: TvShowData.ResultsItem? by bundle(PASS_DATA_TV)
    private val binding by viewBinding<ActivityDetailBinding>()
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setVM()
        setObserve()
        setButton()
        viewModel.setDataDetailMovie(dataMovie)
        viewModel.setDataDetailTv(dataTv)
    }

    private fun setButton() {
        binding.fabShare.setOnClickListener {
            Utils.showToast(this, "Tombol Share Ditekan")
        }
    }

    private fun setVM() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]
    }

    private fun setObserve() {
        val imageLink = "https://image.tmdb.org/t/p/w500"
        if (dataMovie != null) {
            viewModel.getDataDetailMovie().observe(
                this,
                {
                    binding.contentTitle.text = it?.title
                    binding.contentTitle.isSelected = true
                    binding.contentDate.text = it?.releaseDate
                    binding.contentRating.text = it?.voteAverage?.toString()
                    binding.tvOverview.text = it?.overview

                    Glide.with(this)
                        .load(imageLink + it?.posterPath)
                        .centerCrop()
                        .placeholder(android.R.drawable.ic_menu_gallery)
                        .into(binding.ivPict)
                }
            )
        } else {
            viewModel.getDataDetailTv().observe(this, {
                binding.contentTitle.text = it?.name
                binding.contentTitle.isSelected = true
                binding.contentDate.text = it?.firstAirDate
                binding.contentRating.text = it?.voteAverage?.toString()
                binding.tvOverview.text = it?.overview

                Glide.with(this)
                    .load(imageLink + it?.posterPath)
                    .centerCrop()
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .into(binding.ivPict)
            })
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
