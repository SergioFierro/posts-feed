package com.sergiofierro.posts_feed.di

import android.app.Application
import androidx.room.Room
import com.sergiofierro.posts_feed.data.AppDatabase
import com.sergiofierro.posts_feed.data.dao.CommentDao
import com.sergiofierro.posts_feed.data.dao.PostDao
import com.sergiofierro.posts_feed.data.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

  @Provides
  @Singleton
  fun provideAppDatabase(application: Application): AppDatabase =
    Room
      .databaseBuilder(application, AppDatabase::class.java, "mail_app.db")
      .fallbackToDestructiveMigration()
      .build()

  @Provides
  @Singleton
  fun providePostDao(appDatabase: AppDatabase): PostDao = appDatabase.postDao()

  @Provides
  @Singleton
  fun provideCommentDao(appDatabase: AppDatabase): CommentDao = appDatabase.commentDao()

  @Provides
  @Singleton
  fun provideUserDao(appDatabase: AppDatabase): UserDao = appDatabase.userDao()
}
