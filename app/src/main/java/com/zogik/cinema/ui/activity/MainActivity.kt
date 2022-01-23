package com.zogik.cinema.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.test.espresso.idling.CountingIdlingResource
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.zogik.cinema.R
import com.zogik.cinema.databinding.ActivityMainBinding
import com.zogik.cinema.ui.adapter.TabAdapter

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val binding by viewBinding<ActivityMainBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupTab()
    }

    private fun setupTab() {
        val tabTitle =
            arrayOf(getString(R.string.movies_banner), getString(R.string.tv_show_banner))
        val tabAdapter = TabAdapter(supportFragmentManager, lifecycle)
        binding.viewPager.adapter = tabAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }
}
