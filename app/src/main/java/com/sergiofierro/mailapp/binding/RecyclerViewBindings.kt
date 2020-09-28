package com.sergiofierro.mailapp.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sergiofierro.mailapp.model.Comment
import com.sergiofierro.mailapp.model.Post
import com.sergiofierro.mailapp.ui.adapter.CommentAdapter
import com.sergiofierro.mailapp.ui.adapter.PostAdapter

@BindingAdapter("adapter")
fun bindAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
  view.adapter = adapter
}

@BindingAdapter("posts")
fun bindPostList(view: RecyclerView, posts: List<Post>?) {
  posts?.let {
    (view.adapter as? PostAdapter)?.addPosts(it)
  }
}

@BindingAdapter("comments")
fun bindCommentList(view: RecyclerView, comments: List<Comment>?) {
  comments?.let {
    (view.adapter as? CommentAdapter)?.addComments(it)
  }
}
