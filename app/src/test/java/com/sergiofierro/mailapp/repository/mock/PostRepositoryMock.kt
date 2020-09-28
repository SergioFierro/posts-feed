package com.sergiofierro.mailapp.repository.mock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sergiofierro.mailapp.data.Result
import com.sergiofierro.mailapp.data.Result.Success
import com.sergiofierro.mailapp.model.Post
import com.sergiofierro.mailapp.repository.PostRepository
import kotlinx.coroutines.runBlocking

class PostRepositoryMock : PostRepository {

  private var postsServiceData: MutableList<Post> = mutableListOf()
  private val observablePosts = MutableLiveData<Result<List<Post>>>()

  override suspend fun fetchAll(): Result<List<Post>> {
    return Success(postsServiceData)
  }

  override suspend fun delete(post: Post) {
    postsServiceData.remove(post)
  }

  override suspend fun deleteAll() {
    postsServiceData.clear()
  }

  override suspend fun changeFavorite(post: Post) {
    postsServiceData = postsServiceData.map { if (it.id == post.id) post.apply { favorite = !favorite } else it } as MutableList<Post>
  }

  override suspend fun postRead(post: Post) {
    postsServiceData = postsServiceData.map { if (it.id == post.id) post.apply { unread = false } else it } as MutableList<Post>
  }

  override fun observePosts(): LiveData<Result<List<Post>>> {
    runBlocking { refreshPosts() }
    return observablePosts
  }

  private suspend fun refreshPosts() {
    observablePosts.value = fetchAll()
  }

  fun addPosts(posts: List<Post>) {
    postsServiceData.addAll(posts)
    runBlocking { refreshPosts() }
  }
}
