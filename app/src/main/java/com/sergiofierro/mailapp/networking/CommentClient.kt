package com.sergiofierro.mailapp.networking

import com.sergiofierro.mailapp.networking.api.CommentApi
import javax.inject.Inject

class CommentClient @Inject constructor(private val commentApi: CommentApi) {
  suspend fun fetchComments(postId: Int) = commentApi.fetchComments(postId)
}
