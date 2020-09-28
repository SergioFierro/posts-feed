package com.sergiofierro.posts_feed.networking.api

import com.sergiofierro.posts_feed.model.Post
import retrofit2.http.GET

interface PostApi {
  @GET("posts")
  suspend fun fetchPosts(): List<Post>
}
