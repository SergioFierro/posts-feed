package com.sergiofierro.mailapp.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import com.sergiofierro.mailapp.model.Comment

@Dao
interface CommentDao : BaseDao<Comment> {

  @Query("SELECT * FROM Comment")
  fun getAll(): List<Comment>
}
