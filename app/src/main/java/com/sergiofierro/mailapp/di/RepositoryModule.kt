package com.sergiofierro.mailapp.di

import com.sergiofierro.mailapp.networking.PostClient
import com.sergiofierro.mailapp.persistence.dao.PostDao
import com.sergiofierro.mailapp.repository.PostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

  @Provides
  @ActivityRetainedScoped
  fun provideMainRepository(
    postClient: PostClient,
    postDao: PostDao
  ): PostRepository = PostRepository(postClient, postDao)
}
