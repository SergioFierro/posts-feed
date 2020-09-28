package com.sergiofierro.mailapp.di

import com.sergiofierro.mailapp.data.local.PostLocalDataSource
import com.sergiofierro.mailapp.data.remote.PostRemoteDataSource
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
    remoteDataSource: PostRemoteDataSource,
    localDataSource: PostLocalDataSource
  ): PostRepository = PostRepository(remoteDataSource, localDataSource)
}
