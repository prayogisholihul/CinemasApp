package com.zogik.cinema.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zogik.cinema.ui.fragment.FragmentMovies
import com.zogik.cinema.ui.fragment.FragmentTvShow

class MainTabAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragments: ArrayList<Fragment> = arrayListOf(
        FragmentMovies(),
        FragmentTvShow()
    )

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FragmentMovies()
            else -> FragmentTvShow()
        }
    }
}
