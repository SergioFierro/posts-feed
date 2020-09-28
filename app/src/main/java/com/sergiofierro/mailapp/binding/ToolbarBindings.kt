package com.sergiofierro.mailapp.binding

import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.appbar.MaterialToolbar
import com.sergiofierro.mailapp.R

@BindingAdapter("favorite")
fun bindFavorite(view: MaterialToolbar, isFavorite: Boolean) {
  if (isFavorite) {
    view.menu.findItem(R.id.favorite).icon = ContextCompat.getDrawable(view.context, R.drawable.ic_star)
  } else {
    view.menu.findItem(R.id.favorite).icon = ContextCompat.getDrawable(view.context, R.drawable.ic_star_border)
  }
}
