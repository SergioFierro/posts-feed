package com.sergiofierro.posts_feed.util

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SwipeToDeleteTouchHelper(private val adapter: SwipeToDelete) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

  override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false

  override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    val position = viewHolder.adapterPosition
    adapter.deleteItem(position)
  }
}

interface SwipeToDelete {
  fun deleteItem(position: Int)
}
