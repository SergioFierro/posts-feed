package com.sergiofierro.mailapp.repository

import androidx.annotation.WorkerThread
import com.sergiofierro.mailapp.model.Post
import com.sergiofierro.mailapp.networking.PostClient
import com.sergiofierro.mailapp.persistence.dao.PostDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PostRepository @Inject constructor(
  private val postClient: PostClient,
  private val postDao: PostDao
) {

  @WorkerThread
  suspend fun fetchPosts(): Flow<List<Post>> =
    flow {
      var posts = postDao.getAll()
      if (posts.isEmpty()) {
        posts = postClient.fetchPosts()
        postDao.insert(posts)
      }
      emit(posts)
    }.flowOn(Dispatchers.IO)
}
