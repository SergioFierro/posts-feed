package com.sergiofierro.posts_feed.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sergiofierro.posts_feed.R
import com.sergiofierro.posts_feed.databinding.ItemCommentBinding
import com.sergiofierro.posts_feed.model.Comment

class CommentAdapter : ListAdapter<Comment, CommentAdapter.CommentViewHolder>(PostDiff()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val binding = DataBindingUtil.inflate<ItemCommentBinding>(inflater, R.layout.item_comment, parent, false)
    return CommentViewHolder(binding)
  }

  override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
    val item = getItem(position)
    holder.binding.apply {
      comment = item
      executePendingBindings()
    }
  }

  fun addComments(comments: List<Comment>) {
    submitList(comments)
  }

  inner class CommentViewHolder(val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root)

  private class PostDiff : DiffUtil.ItemCallback<Comment>() {
    override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
      return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
      return oldItem == newItem
    }
  }
}
