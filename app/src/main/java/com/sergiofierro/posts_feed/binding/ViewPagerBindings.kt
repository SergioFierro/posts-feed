package com.sergiofierro.posts_feed.binding

import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

@BindingAdapter("viewPagerAdapter")
fun bindViewPagerAdapter(view: ViewPager, adapter: PagerAdapter) {
  view.adapter = adapter
}

