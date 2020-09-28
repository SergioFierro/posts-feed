package com.sergiofierro.mailapp.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.sergiofierro.mailapp.data.Result
import com.sergiofierro.mailapp.data.Result.Error
import com.sergiofierro.mailapp.data.Result.Success
import com.sergiofierro.mailapp.data.local.PostLocalDataSource
import com.sergiofierro.mailapp.data.remote.PostRemoteDataSource
import com.sergiofierro.mailapp.model.Post
import javax.inject.Inject

class PostRepository @Inject constructor(
  private val remoteDataSource: PostRemoteDataSource,
  private val localDataSource: PostLocalDataSource
) {

  @WorkerThread
  suspend fun fetchAll(): Result<List<Post>> {
    try {
      val postsResponse = remoteDataSource.fetchPosts()
      if (postsResponse is Success) {
        localDataSource.deleteAll()
        localDataSource.save(postsResponse.data)
      } else if (postsResponse is Error) {
        throw postsResponse.exception
      }
    } catch (ex: Exception) {
      return Error(ex)
    }
    return localDataSource.getAll()
  }

  @WorkerThread
  suspend fun delete(post: Post) {
    localDataSource.delete(post)
  }

  @WorkerThread
  suspend fun deleteAll() {
    localDataSource.deleteAll()
  }

  @WorkerThread
  suspend fun changeFavorite(post: Post) {
    post.favorite = !post.favorite
    localDataSource.save(post)
  }

  @WorkerThread
  suspend fun postRead(post: Post) {
    post.unread = false
    localDataSource.save(post)
  }

  fun observePosts(): LiveData<Result<List<Post>>> = localDataSource.observePosts()
}
