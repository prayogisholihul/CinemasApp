package com.zogik.cinema.ui.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skydoves.bundler.bundle
import com.skydoves.bundler.intentOf
import com.zogik.cinema.R
import com.zogik.cinema.data.MovieData
import com.zogik.cinema.databinding.ActivityDetailBinding
import com.zogik.cinema.ui.viewmodel.DetailViewModel

class ActivityDetail : AppCompatActivity(R.layout.activity_detail) {
    private val data: MovieData.ResultsItem? by bundle(PASS_DATA)
    private val binding by viewBinding<ActivityDetailBinding>()
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setVM()
        setObserve()
        viewModel.setDataDetail(data)
    }

    private fun setVM() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]
    }

    private fun setObserve() {
        viewModel.getDataDetail().observe(
            this,
            {
                binding.movieTitle.text = it?.title
                binding.movieTitle.isSelected = true
                binding.movieDate.text = it?.releaseDate
                binding.movieRating.text = it?.voteAverage?.toString()
                binding.tvOverview.text = it?.overview
            }
        )
    }

    companion object {
        const val PASS_DATA = "MOVIE"

        fun Context.passToDetail(data: MovieData.ResultsItem) = intentOf<ActivityDetail> {
            +(PASS_DATA to data)
            startActivity(this@passToDetail)
        }
    }
}
