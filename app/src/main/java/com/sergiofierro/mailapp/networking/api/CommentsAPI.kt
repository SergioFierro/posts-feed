package com.sergiofierro.mailapp.networking.api

import com.sergiofierro.mailapp.model.Comment
import retrofit2.http.GET
import retrofit2.http.Path

interface CommentsAPI {
  @GET("posts/{id}/comments")
  suspend fun fetchComments(@Path("id") id: Int): List<Comment>
}
