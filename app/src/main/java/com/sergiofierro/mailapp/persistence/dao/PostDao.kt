package com.sergiofierro.mailapp.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import com.sergiofierro.mailapp.model.Post

@Dao
interface PostDao : BaseDao<Post> {

  @Query("SELECT * FROM Post")
  fun getAll(): List<Post>

  @Query("DELETE FROM Post")
  fun deleteAll()
}
