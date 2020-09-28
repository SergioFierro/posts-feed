package com.sergiofierro.posts_feed.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sergiofierro.posts_feed.model.Comment
import com.sergiofierro.posts_feed.model.Post
import com.sergiofierro.posts_feed.model.User
import com.sergiofierro.posts_feed.data.dao.CommentDao
import com.sergiofierro.posts_feed.data.dao.PostDao
import com.sergiofierro.posts_feed.data.dao.UserDao

@Database(entities = [Post::class, Comment::class, User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
  abstract fun postDao(): PostDao
  abstract fun commentDao(): CommentDao
  abstract fun userDao(): UserDao
}
