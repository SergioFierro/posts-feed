package com.sergiofierro.mailapp.di

import android.app.Application
import androidx.room.Room
import com.sergiofierro.mailapp.data.AppDatabase
import com.sergiofierro.mailapp.data.dao.CommentDao
import com.sergiofierro.mailapp.data.dao.PostDao
import com.sergiofierro.mailapp.data.dao.UserDao
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
