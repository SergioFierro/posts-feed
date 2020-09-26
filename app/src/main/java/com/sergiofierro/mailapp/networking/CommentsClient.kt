package com.sergiofierro.mailapp.networking

import com.sergiofierro.mailapp.networking.api.CommentsApi
import javax.inject.Inject

class CommentsClient @Inject constructor(private val commentsApi: CommentsApi) {
  suspend fun fetchComments(postId: Int) = commentsApi.fetchComments(postId)
}
