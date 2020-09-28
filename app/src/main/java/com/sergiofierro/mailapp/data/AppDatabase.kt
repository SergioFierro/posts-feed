package com.sergiofierro.mailapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sergiofierro.mailapp.model.Comment
import com.sergiofierro.mailapp.model.Post
import com.sergiofierro.mailapp.model.User
import com.sergiofierro.mailapp.data.dao.CommentDao
import com.sergiofierro.mailapp.data.dao.PostDao
import com.sergiofierro.mailapp.data.dao.UserDao

@Database(entities = [Post::class, Comment::class, User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
  abstract fun postDao(): PostDao
  abstract fun commentDao(): CommentDao
  abstract fun userDao(): UserDao
}
