package com.sergiofierro.mailapp.repository.mock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sergiofierro.mailapp.data.Result
import com.sergiofierro.mailapp.model.Comment
import com.sergiofierro.mailapp.repository.CommentRepository
import kotlinx.coroutines.runBlocking

class CommentRepositoryMock : CommentRepository {

  var commentServiceData: MutableList<Comment> = mutableListOf()
  private val observableComments = MutableLiveData<Result<List<Comment>>>()

  override suspend fun fetchComments(postId: Int): Result<List<Comment>> {
    return Result.Success(commentServiceData.filter { it.postId == postId })
  }

  override fun observerComments(postId: Int): LiveData<Result<List<Comment>>> {
    runBlocking { refreshPosts(postId) }
    return observableComments
  }

  private suspend fun refreshPosts(postId: Int) {
    observableComments.value = fetchComments(postId)
  }

  fun addComments(comments: List<Comment>) {
    commentServiceData.addAll(comments)
  }
}
