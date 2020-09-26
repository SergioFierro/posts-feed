package com.sergiofierro.mailapp.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import com.sergiofierro.mailapp.model.User

@Dao
interface UserDao : BaseDao<User> {

  @Query("SELECT * FROM User")
  fun getAll(): List<User>

  @Query("SELECT * FROM User WHERE id=:id")
  fun get(id: Int): User?

  @Query("DELETE FROM User")
  fun deleteAll()
}
