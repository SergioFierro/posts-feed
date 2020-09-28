package com.sergiofierro.posts_feed.di

import com.sergiofierro.posts_feed.BuildConfig
import com.sergiofierro.posts_feed.networking.api.CommentApi
import com.sergiofierro.posts_feed.networking.api.PostApi
import com.sergiofierro.posts_feed.networking.api.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @Provides
  @Singleton
  fun provideOkHttpClient(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor().apply {
      setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    return OkHttpClient.Builder()
      .addInterceptor(loggingInterceptor)
      .build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(okHttpClient).addConverterFactory(MoshiConverterFactory.create()).build()

  @Provides
  @Singleton
  fun providePostApi(retrofit: Retrofit): PostApi = retrofit.create(PostApi::class.java)

  @Provides
  @Singleton
  fun provideUserApi(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)

  @Provides
  @Singleton
  fun provideCommentsApi(retrofit: Retrofit): CommentApi = retrofit.create(CommentApi::class.java)
}
