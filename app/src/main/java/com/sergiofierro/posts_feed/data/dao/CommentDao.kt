package com.sergiofierro.posts_feed.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.sergiofierro.posts_feed.model.Comment

@Dao
interface CommentDao : BaseDao<Comment> {

  @Query("SELECT * FROM Comment WHERE postId=:postId")
  fun getByPostId(postId: Int): List<Comment>

  @Query("SELECT * FROM Comment WHERE postId=:postId")
  fun observeComments(postId: Int): LiveData<List<Comment>>
}
