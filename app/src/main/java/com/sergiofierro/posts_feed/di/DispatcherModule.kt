package com.sergiofierro.posts_feed.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ActivityRetainedComponent::class)
object DispatcherModule {

  @Provides
  fun providesBackgroundDispatcher(): CoroutineDispatcher {
    return Dispatchers.IO
  }
}
