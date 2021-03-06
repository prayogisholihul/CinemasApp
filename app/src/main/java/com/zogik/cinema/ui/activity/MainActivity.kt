package com.zogik.cinema.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.zogik.cinema.R
import com.zogik.cinema.databinding.ActivityTabBaseBinding
import com.zogik.cinema.ui.adapter.MainTabAdapter

class MainActivity : AppCompatActivity(R.layout.activity_tab_base) {
    private val binding by viewBinding<ActivityTabBaseBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "Home"
        setupTab()
    }

    private fun setupTab() {
        val tabTitle =
            arrayOf(getString(R.string.movies_banner), getString(R.string.tv_show_banner))
        val tabAdapter = MainTabAdapter(supportFragmentManager, lifecycle)
        binding.viewPager.adapter = tabAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorite -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}
