package com.sergiofierro.mailapp.networking

import com.sergiofierro.mailapp.networking.api.CommentsAPI
import javax.inject.Inject

class CommentsClient @Inject constructor(private val commentsAPI: CommentsAPI) {
  suspend fun fetchComments(postId: Int) = commentsAPI.fetchComments(postId)
}
