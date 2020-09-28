package com.sergiofierro.posts_feed.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sergiofierro.posts_feed.R
import com.sergiofierro.posts_feed.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  private val mainViewModel: MainViewModel by viewModels()
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    binding.apply {
      lifecycleOwner = this@MainActivity
      adapter = SectionsPagerAdapter(this@MainActivity, supportFragmentManager)
      vm = mainViewModel
      tabs.setupWithViewPager(viewPager, true)
    }
    binding.toolbar.setOnMenuItemClickListener { menuItem ->
      when (menuItem.itemId) {
        R.id.refresh -> {
          mainViewModel.refresh()
          true
        }
        else -> false
      }
    }

    mainViewModel.refresh()
  }
}
