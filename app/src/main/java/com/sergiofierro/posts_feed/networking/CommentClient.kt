package com.sergiofierro.posts_feed.networking

import com.sergiofierro.posts_feed.networking.api.CommentApi
import javax.inject.Inject

class CommentClient @Inject constructor(private val commentApi: CommentApi) {
  suspend fun fetchComments(postId: Int) = commentApi.fetchComments(postId)
}
