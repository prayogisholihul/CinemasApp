package com.zogik.cinema.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.zogik.cinema.R
import com.zogik.cinema.data.room.local.MovieEntity
import com.zogik.cinema.databinding.FragmentContentBinding
import com.zogik.cinema.ui.activity.ActivityDetail.Companion.passToDetailMovie
import com.zogik.cinema.ui.adapter.MovieAdapter
import com.zogik.cinema.ui.viewmodel.ViewModelMovies
import com.zogik.cinema.utils.IdlingResource
import com.zogik.cinema.utils.Result
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
    }

    private fun setupObserver() {
        IdlingResource.increment()
        viewModelMovies.moviesPagingData().observe(viewLifecycleOwner) {
            when (it.status) {
                Result.Status.LOADING -> {
                    viewVisible(binding.loading)
                }
                Result.Status.SUCCESS -> {
                    viewGone(binding.loading)
                    viewLifecycleOwner.lifecycleScope.launchWhenResumed {
                        it.data?.let { data ->
                            adapterMovies.submitData(data)
                        }
                    }
                    IdlingResource.decrement()
                }
                Result.Status.ERROR -> {
                    viewGone(binding.loading)
                    showToast(requireContext(), getString(R.string.toast_text))
                    IdlingResource.decrement()
                }
            }
        }
    }

    private fun setupView() {
        adapterMovies = MovieAdapter(
            requireContext(),
            object : MovieAdapter.OnClickListener {
                override fun setonClick(data: MovieEntity) {
                    requireContext().passToDetailMovie(data)
                }
            }
        )
        binding.rvContent.layoutManager = LinearLayoutManager(requireContext())
        binding.rvContent.adapter = adapterMovies
    }
}
