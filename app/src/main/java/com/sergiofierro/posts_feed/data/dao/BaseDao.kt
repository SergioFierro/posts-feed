package com.sergiofierro.posts_feed.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface BaseDao<in T> {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(vararg entity: T)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(entity: List<T>)

  @Delete
  suspend fun delete(entity: T)
}
