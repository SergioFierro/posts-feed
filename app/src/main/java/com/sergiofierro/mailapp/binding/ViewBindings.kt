package com.sergiofierro.mailapp.binding

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("hidden")
fun bindGone(view: View, hide: Boolean) {
  view.visibility = if (hide) {
    View.GONE
  } else {
    View.VISIBLE
  }
}
