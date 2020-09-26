package com.sergiofierro.mailapp.networking.api

import com.sergiofierro.mailapp.model.Post
import retrofit2.http.GET

interface PostAPI {
  @GET("posts")
  suspend fun fetchPosts(): List<Post>
}
