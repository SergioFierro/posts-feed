package com.sergiofierro.posts_feed.networking.api

import com.sergiofierro.posts_feed.model.Comment
import retrofit2.http.GET
import retrofit2.http.Path

interface CommentApi {
  @GET("posts/{id}/comments")
  suspend fun fetchComments(@Path("id") id: Int): List<Comment>
}
