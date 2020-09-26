package com.sergiofierro.mailapp.networking.api

import com.sergiofierro.mailapp.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface UserAPI {
  @GET("users/{id}")
  suspend fun fetchUser(@Path("id") id: Int): User
}
