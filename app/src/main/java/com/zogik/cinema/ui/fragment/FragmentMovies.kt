package com.zogik.cinema.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.zogik.cinema.R
import com.zogik.cinema.data.MovieData
import com.zogik.cinema.databinding.FragmentContentBinding
import com.zogik.cinema.ui.activity.ActivityDetail.Companion.passToDetailMovie
import com.zogik.cinema.ui.adapter.MovieAdapter
import com.zogik.cinema.ui.viewmodel.ViewModelMovies
import com.zogik.cinema.utils.IdlingResource
import com.zogik.cinema.utils.State
import com.zogik.cinema.utils.Utils.showToast
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

        viewModelMovies.getMovies()
    }

    private fun setupObserver() {

        viewModelMovies.moviesData.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> {
                    viewVisible(binding.loading)
                }
                is State.Success -> {
                    IdlingResource.increment()
                    Handler(Looper.getMainLooper()).postDelayed({
                        viewGone(binding.loading)
                        adapterMovies.setData(it.data?.results)
                        IdlingResource.decrement()
                    }, 4000)
                }
                is State.Error -> {
                    viewGone(binding.loading)
                    viewVisible(binding.tvNoDataFound)
                    showToast(requireContext(), getString(R.string.toast_text))
                }
            }
        }
    }

    private fun setupView() {
        adapterMovies = MovieAdapter(
            arrayListOf(), requireContext(),
            object : MovieAdapter.OnClickListener {
                override fun setonClick(data: MovieData.ResultsItem) {
                    requireContext().passToDetailMovie(data)
                }
            }
        )
        binding.rvContent.layoutManager = LinearLayoutManager(requireContext())
        binding.rvContent.adapter = adapterMovies
    }
}
