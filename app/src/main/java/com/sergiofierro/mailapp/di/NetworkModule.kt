package com.sergiofierro.mailapp.di

import com.sergiofierro.mailapp.BuildConfig
import com.sergiofierro.mailapp.networking.api.CommentsAPI
import com.sergiofierro.mailapp.networking.api.PostAPI
import com.sergiofierro.mailapp.networking.api.UserAPI
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
  fun providePostAPI(retrofit: Retrofit): PostAPI = retrofit.create(PostAPI::class.java)

  @Provides
  @Singleton
  fun provideUserAPI(retrofit: Retrofit): UserAPI = retrofit.create(UserAPI::class.java)

  @Provides
  @Singleton
  fun provideCommentsAPI(retrofit: Retrofit): CommentsAPI = retrofit.create(CommentsAPI::class.java)
}
