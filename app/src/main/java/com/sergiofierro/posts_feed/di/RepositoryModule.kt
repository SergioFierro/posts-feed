package com.sergiofierro.posts_feed.di

import com.sergiofierro.posts_feed.data.local.CommentLocalDataSource
import com.sergiofierro.posts_feed.data.local.PostLocalDataSource
import com.sergiofierro.posts_feed.data.local.UserLocalDataSource
import com.sergiofierro.posts_feed.data.remote.CommentRemoteDataSource
import com.sergiofierro.posts_feed.data.remote.PostRemoteDataSource
import com.sergiofierro.posts_feed.data.remote.UserRemoteDataSource
import com.sergiofierro.posts_feed.repository.*
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
