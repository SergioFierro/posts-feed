package com.sergiofierro.posts_feed.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sergiofierro.posts_feed.R

enum class ListType(val title: Int) {
  ALL(R.string.tab_text_1),
  FAVORITES(R.string.tab_text_2)
}

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

  override fun getItem(position: Int): Fragment =
    PostFragment.newInstance(ListType.values()[position])

  override fun getPageTitle(position: Int): CharSequence? =
    context.resources.getString(ListType.values()[position].title)

  override fun getCount() = ListType.values().size
}
