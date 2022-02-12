package com.zogik.cinema.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.zogik.cinema.R
import com.zogik.cinema.data.room.favorite.MovieFavoriteEntity
import com.zogik.cinema.databinding.FragmentContentBinding
import com.zogik.cinema.ui.activity.ActivityDetail.Companion.passFromFavMovie
import com.zogik.cinema.ui.adapter.MovieFavoriteAdapter
import com.zogik.cinema.ui.viewmodel.ViewModelFavoriteMovie
import com.zogik.cinema.utils.IdlingResource
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentFavoriteMovie : Fragment(R.layout.fragment_content) {

    private val binding by viewBinding<FragmentContentBinding>()
    private val viewModel by viewModel<ViewModelFavoriteMovie>()
    private lateinit var adapter: MovieFavoriteAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        setRV()
    }

    private fun setupObserver() {
        IdlingResource.increment()
        viewModel.getFavoriteMovie().observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                adapter.submitData(it)
            }
            IdlingResource.decrement()
        }
    }

    private fun setRV() {
        adapter = MovieFavoriteAdapter(
            requireContext(),
            object : MovieFavoriteAdapter.OnClickListener {
                override fun setonClick(data: MovieFavoriteEntity) {
                    requireContext().passFromFavMovie(data)
                }
            }
        )
        binding.rvContent.layoutManager = LinearLayoutManager(requireContext())
        binding.rvContent.adapter = adapter
    }
}
