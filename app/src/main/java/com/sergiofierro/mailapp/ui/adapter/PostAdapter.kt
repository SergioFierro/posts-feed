package com.sergiofierro.mailapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sergiofierro.mailapp.R
import com.sergiofierro.mailapp.databinding.ItemPostBinding
import com.sergiofierro.mailapp.model.Post
import com.sergiofierro.mailapp.ui.detail.DetailActivity
import com.sergiofierro.mailapp.util.SwipeToDelete

class PostAdapter(private val itemRemoved: (Post) -> Unit) : ListAdapter<Post, PostAdapter.PostViewHolder>(PostDiff()), SwipeToDelete {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val binding = DataBindingUtil.inflate<ItemPostBinding>(inflater, R.layout.item_post, parent, false)
    return PostViewHolder(binding)
  }

  override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
    val item = getItem(position)
    holder.binding.apply {
      post = item
      executePendingBindings()
      root.setOnClickListener {
        DetailActivity.startActivity(it.context, item)
      }
    }
  }

  fun addPosts(posts: List<Post>) {
    submitList(posts)
  }

  override fun deleteItem(position: Int) {
    itemRemoved(currentList[position])
  }

  inner class PostViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root)

  private class PostDiff : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
      return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
      return oldItem == newItem
    }
  }
}
