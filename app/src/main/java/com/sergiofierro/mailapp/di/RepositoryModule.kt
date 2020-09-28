package com.sergiofierro.mailapp.di

import com.sergiofierro.mailapp.data.local.CommentLocalDataSource
import com.sergiofierro.mailapp.data.local.PostLocalDataSource
import com.sergiofierro.mailapp.data.local.UserLocalDataSource
import com.sergiofierro.mailapp.data.remote.CommentRemoteDataSource
import com.sergiofierro.mailapp.data.remote.PostRemoteDataSource
import com.sergiofierro.mailapp.data.remote.UserRemoteDataSource
import com.sergiofierro.mailapp.repository.*
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
  fun providePostRepository(
    remoteDataSource: PostRemoteDataSource,
    localDataSource: PostLocalDataSource
  ): PostRepository = DefaultPostRepository(remoteDataSource, localDataSource)

  @Provides
  @ActivityRetainedScoped
  fun provideCommentRepository(
    remoteDataSource: CommentRemoteDataSource,
    localDataSource: CommentLocalDataSource
  ): CommentRepository = DefaultCommentRepository(remoteDataSource, localDataSource)

  @Provides
  @ActivityRetainedScoped
  fun provideUserRepository(
    remoteDataSource: UserRemoteDataSource,
    localDataSource: UserLocalDataSource
  ): UserRepository = DefaultUserRepository(remoteDataSource, localDataSource)
}
