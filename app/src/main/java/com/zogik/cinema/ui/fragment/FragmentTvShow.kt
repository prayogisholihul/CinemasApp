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
import com.zogik.cinema.ui.adapter.TvShowAdapter
import com.zogik.cinema.ui.viewmodel.TvShowFactory
import com.zogik.cinema.ui.viewmodel.ViewModelTvShow
import com.zogik.cinema.utils.State
import com.zogik.cinema.utils.Utils

class FragmentTvShow : Fragment(R.layout.fragment_content) {
    private val api by lazy { ApiNetwork.getClient() }
    private val binding by viewBinding<FragmentContentBinding>()
    private lateinit var adapterTvShow: TvShowAdapter
    private lateinit var viewModelTvShow: ViewModelTvShow
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupObserver()
        setupView()
    }

    private fun setupViewModel() {
        viewModelTvShow = ViewModelProvider(
            this,
            TvShowFactory(Repository(api))
        )[ViewModelTvShow::class.java]
    }

    private fun setupObserver() {
        viewModelTvShow.tvShowData.observe(viewLifecycleOwner, {
            when (it) {
                is State.Loading -> {
                    Utils.viewVisible(binding.loading)
                }
                is State.Success -> {
                    Utils.viewGone(binding.loading)
                    adapterTvShow.setData(it.data?.results)
                }
                is State.Error -> {
                    Utils.viewGone(binding.loading)
                    Utils.viewVisible(binding.tvNoDataFound)
                    Utils.showToast(requireContext(), "Data Can't be Loaded")
                }
            }
        })
    }

    private fun setupView() {
        adapterTvShow = TvShowAdapter(
            arrayListOf(), requireContext()
        )
        binding.rvContent.layoutManager = LinearLayoutManager(requireContext())
        binding.rvContent.adapter = adapterTvShow
    }
}
