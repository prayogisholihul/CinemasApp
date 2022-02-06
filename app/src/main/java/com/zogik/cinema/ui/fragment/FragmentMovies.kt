package com.zogik.cinema.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.zogik.cinema.R
import com.zogik.cinema.data.ResultsItem
import com.zogik.cinema.databinding.FragmentContentBinding
import com.zogik.cinema.ui.adapter.MovieAdapter
import com.zogik.cinema.ui.viewmodel.ViewModelMovies
import com.zogik.cinema.utils.Result
import com.zogik.cinema.utils.Utils.viewGone
import com.zogik.cinema.utils.Utils.viewVisible
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentMovies : Fragment(R.layout.fragment_content) {
    private val binding by viewBinding<FragmentContentBinding>()
    private lateinit var adapterMovies: MovieAdapter
    private val viewModelMovies: ViewModelMovies by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        setupView()
    }

    private fun setupObserver() {
        viewModelMovies.moviesPagingData.observe(viewLifecycleOwner) {
            when (it.status) {
                Result.Status.LOADING -> {
                    viewVisible(binding.loading)
                }
                Result.Status.SUCCESS -> {
                    viewGone(binding.loading)
                    adapterMovies.setData(it.data)
                }
                Result.Status.ERROR -> {}
            }
        }
    }

    private fun setupView() {
        adapterMovies = MovieAdapter(
            arrayListOf(), requireContext(),
            object : MovieAdapter.OnClickListener {
                override fun setonClick(data: ResultsItem) {
//                    requireContext().passToDetailMovie(data)
                }
            }
        )
        binding.rvContent.layoutManager = LinearLayoutManager(requireContext())
        binding.rvContent.adapter = adapterMovies
    }
}
