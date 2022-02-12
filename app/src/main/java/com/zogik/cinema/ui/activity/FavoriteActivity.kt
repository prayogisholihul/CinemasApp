package com.zogik.cinema.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.zogik.cinema.R
import com.zogik.cinema.databinding.ActivityTabBaseBinding
import com.zogik.cinema.ui.adapter.FavoriteTabAdapter

class FavoriteActivity : AppCompatActivity(R.layout.activity_tab_base) {
    private val binding by viewBinding<ActivityTabBaseBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "Favorite"
        setupTab()
    }

    private fun setupTab() {
        val tabTitle =
            arrayOf(getString(R.string.movies_banner), getString(R.string.tv_show_banner))
        val tabAdapter = FavoriteTabAdapter(supportFragmentManager, lifecycle)
        binding.viewPager.adapter = tabAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }
}
