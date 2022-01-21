package com.zogik.cinema.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.zogik.cinema.R
import com.zogik.cinema.data.Repository
import com.zogik.cinema.databinding.FragmentContentBinding
import com.zogik.cinema.network.ApiNetwork
import com.zogik.cinema.ui.adapter.MovieAdapter
import com.zogik.cinema.ui.viewmodel.MoviesFactory
import com.zogik.cinema.ui.viewmodel.ViewModelMovies
import com.zogik.cinema.utils.Resource
import com.zogik.cinema.utils.Widget.showToast
import com.zogik.cinema.utils.Widget.viewGone
import com.zogik.cinema.utils.Widget.viewVisible

class FragmentMovies : Fragment(R.layout.fragment_content) {
    private val api by lazy { ApiNetwork.getClient() }
    private val binding by viewBinding<FragmentContentBinding>()
    private lateinit var adapterMovies: MovieAdapter
    private lateinit var viewModelMovies: ViewModelMovies
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupObserver()
        setupView()
    }

    private fun setupViewModel() {
        viewModelMovies = ViewModelProvider(
            this,
            MoviesFactory(Repository(api))
        )[ViewModelMovies::class.java]
    }

    private fun setupObserver() {
        viewModelMovies.moviesData.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    viewVisible(binding.loading)
                }
                is Resource.Success -> {
                    viewGone(binding.loading)
                    adapterMovies.setData(it.data?.results)
                }
                is Resource.Error -> {
                    viewGone(binding.loading)
                    viewVisible(binding.tvNoDataFound)
                    showToast(requireContext(), "Data Can't be Loaded")
                }
            }
        })
    }

    private fun setupView() {
        adapterMovies = MovieAdapter(
            arrayListOf(), requireContext()
        )
        binding.rvContent.layoutManager = LinearLayoutManager(requireContext())
        binding.rvContent.adapter = adapterMovies
    }
}
