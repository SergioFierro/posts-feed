package com.sergiofierro.mailapp.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import com.sergiofierro.mailapp.R
import com.sergiofierro.mailapp.databinding.ActivityDetailBinding
import com.sergiofierro.mailapp.model.Post
import com.sergiofierro.mailapp.ui.adapter.CommentAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

  private val detailViewModel: DetailViewModel by viewModels()
  private lateinit var binding: ActivityDetailBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val postItem: Post = requireNotNull(intent.getParcelableExtra(ARG_POST))
    detailViewModel.setPost(postItem)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
    binding.apply {
      lifecycleOwner = this@DetailActivity
      post = postItem
      vm = detailViewModel
      adapter = CommentAdapter()
      recyclerView.addItemDecoration(DividerItemDecoration(this@DetailActivity, DividerItemDecoration.VERTICAL))
    }
    binding.toolbar.setNavigationOnClickListener {
      onBackPressed()
    }
    binding.toolbar.setOnMenuItemClickListener { menuItem ->
      when (menuItem.itemId) {
        R.id.favorite -> {
          detailViewModel.changeFavorite()
          true
        }
        else -> false
      }
    }
  }

  companion object {

    private const val ARG_POST = "post"

    fun startActivity(context: Context, post: Post) {
      Intent(context, DetailActivity::class.java).apply {
        putExtra(ARG_POST, post)
      }.also { context.startActivity(it) }
    }
  }
}
