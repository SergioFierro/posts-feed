package com.sergiofierro.posts_feed.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.sergiofierro.posts_feed.model.User

@Dao
interface UserDao : BaseDao<User> {

  @Query("SELECT * FROM User WHERE id=:id")
  fun get(id: Int): User?

  @Query("SELECT * FROM User WHERE id=:id")
  fun observeUser(id: Int): LiveData<User?>
}
