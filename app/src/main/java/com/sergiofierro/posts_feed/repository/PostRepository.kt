package com.sergiofierro.posts_feed.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.sergiofierro.posts_feed.data.Result
import com.sergiofierro.posts_feed.data.Result.Error
import com.sergiofierro.posts_feed.data.Result.Success
import com.sergiofierro.posts_feed.data.local.PostLocalDataSource
import com.sergiofierro.posts_feed.data.remote.PostRemoteDataSource
import com.sergiofierro.posts_feed.model.Post
import javax.inject.Inject

interface PostRepository {
  suspend fun fetchAll(): Result<List<Post>>
  suspend fun delete(post: Post)
  suspend fun deleteAll()
  suspend fun changeFavorite(post: Post)
  suspend fun postRead(post: Post)
  fun observePosts(): LiveData<Result<List<Post>>>
}

class DefaultPostRepository @Inject constructor(
  private val remoteDataSource: PostRemoteDataSource,
  private val localDataSource: PostLocalDataSource
) : PostRepository {

  @WorkerThread
  override suspend fun fetchAll(): Result<List<Post>> {
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
  override suspend fun delete(post: Post) {
    localDataSource.delete(post)
  }

  @WorkerThread
  override suspend fun deleteAll() {
    localDataSource.deleteAll()
  }

  @WorkerThread
  override suspend fun changeFavorite(post: Post) {
    post.favorite = !post.favorite
    localDataSource.save(post)
  }

  @WorkerThread
  override suspend fun postRead(post: Post) {
    post.unread = false
    localDataSource.save(post)
  }

  override fun observePosts(): LiveData<Result<List<Post>>> = localDataSource.observePosts()
}
