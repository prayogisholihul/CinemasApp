package com.zogik.cinema.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zogik.cinema.ui.fragment.FragmentFavoriteMovie
import com.zogik.cinema.ui.fragment.FragmentFavoriteTvShow

class FavoriteTabAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragments: ArrayList<Fragment> = arrayListOf(
        FragmentFavoriteMovie(),
        FragmentFavoriteTvShow()
    )

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FragmentFavoriteMovie()
            else -> FragmentFavoriteTvShow()
        }
    }
}
