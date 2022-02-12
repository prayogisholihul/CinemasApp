package com.zogik.cinema.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.zogik.cinema.R
import com.zogik.cinema.data.room.favorite.TvFavoriteEntity
import com.zogik.cinema.databinding.FragmentContentBinding
import com.zogik.cinema.ui.activity.ActivityDetail.Companion.passFromFavTv
import com.zogik.cinema.ui.adapter.TvFavoriteAdapter
import com.zogik.cinema.ui.viewmodel.ViewModelFavoriteTv
import com.zogik.cinema.utils.IdlingResource
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentFavoriteTvShow : Fragment(R.layout.fragment_content) {
    private val binding by viewBinding<FragmentContentBinding>()
    private val viewModel by viewModel<ViewModelFavoriteTv>()
    private lateinit var adapter: TvFavoriteAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        setRV()
    }

    private fun setupObserver() {
        IdlingResource.increment()
        viewModel.getFavoriteTv().observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                adapter.submitData(it)
            }
            IdlingResource.decrement()
        }
    }

    private fun setRV() {
        adapter = TvFavoriteAdapter(
            requireContext(),
            object : TvFavoriteAdapter.OnClickListener {
                override fun onClick(data: TvFavoriteEntity) {
                    requireContext().passFromFavTv(data)
                }
            }
        )
        binding.rvContent.layoutManager = LinearLayoutManager(requireContext())
        binding.rvContent.adapter = adapter
    }
}
