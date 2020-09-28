package com.sergiofierro.mailapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.sergiofierro.mailapp.model.Post
import com.sergiofierro.mailapp.model.User

@Dao
interface UserDao : BaseDao<User> {

  @Query("SELECT * FROM User WHERE id=:id")
  fun get(id: Int): User?

  @Query("SELECT * FROM User WHERE id=:id")
  fun observeUser(id: Int): LiveData<User?>
}
