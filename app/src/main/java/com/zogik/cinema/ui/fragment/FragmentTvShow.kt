package com.zogik.cinema.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.zogik.cinema.R
import com.zogik.cinema.data.room.local.TvEntity
import com.zogik.cinema.databinding.FragmentContentBinding
import com.zogik.cinema.ui.activity.ActivityDetail.Companion.passToDetailTv
import com.zogik.cinema.ui.adapter.TvShowAdapter
import com.zogik.cinema.ui.viewmodel.ViewModelTvShow
import com.zogik.cinema.utils.IdlingResource
import com.zogik.cinema.utils.Result
import com.zogik.cinema.utils.Utils
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentTvShow : Fragment(R.layout.fragment_content) {
    private val binding by viewBinding<FragmentContentBinding>()
    private lateinit var adapterTvShow: TvShowAdapter
    private val viewModelTvShow: ViewModelTvShow by viewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver()
        setupView()
    }

    private fun setupObserver() {
        IdlingResource.increment()
        viewModelTvShow.tvPagingData().observe(viewLifecycleOwner) {
            when (it.status) {
                Result.Status.LOADING -> {
                    Utils.viewVisible(binding.loading)
                }
                Result.Status.SUCCESS -> {
                    Utils.viewGone(binding.loading)
                    viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                        it.data?.let { it1 -> adapterTvShow.submitData(it1) }
                    }
                    IdlingResource.decrement()
                }
                Result.Status.ERROR -> {
                    Utils.viewGone(binding.loading)
                    Utils.viewVisible(binding.tvNoDataFound)
                    Utils.showToast(requireContext(), "Data Can't be Loaded")
                    IdlingResource.decrement()
                }
            }
        }
        IdlingResource.decrement()
    }

    private fun setupView() {
        adapterTvShow = TvShowAdapter(
            requireContext(),
            object : TvShowAdapter.OnClickListener {
                override fun setonClick(data: TvEntity) {
                    requireContext().passToDetailTv(data)
                }
            }
        )
        binding.rvContent.layoutManager = LinearLayoutManager(requireContext())
        binding.rvContent.adapter = adapterTvShow
    }
}
