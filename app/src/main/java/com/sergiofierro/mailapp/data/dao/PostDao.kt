package com.sergiofierro.mailapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.sergiofierro.mailapp.model.Post

@Dao
interface PostDao : BaseDao<Post> {

  @Query("SELECT * FROM Post")
  fun observePosts(): LiveData<List<Post>>

  @Query("SELECT * FROM Post")
  suspend fun getAll(): List<Post>

  @Query("DELETE FROM Post")
  fun deleteAll()
}
