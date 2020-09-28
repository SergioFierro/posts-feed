package com.sergiofierro.posts_feed.networking.api

import com.sergiofierro.posts_feed.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {
  @GET("users/{id}")
  suspend fun fetchUser(@Path("id") id: Int): User
}
