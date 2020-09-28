package com.sergiofierro.posts_feed.ui.detail

import androidx.annotation.VisibleForTesting
import androidx.databinding.ObservableBoolean
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.sergiofierro.posts_feed.data.Result
import com.sergiofierro.posts_feed.data.Result.Success
import com.sergiofierro.posts_feed.model.Comment
import com.sergiofierro.posts_feed.model.Post
import com.sergiofierro.posts_feed.model.User
import com.sergiofierro.posts_feed.repository.CommentRepository
import com.sergiofierro.posts_feed.repository.PostRepository
import com.sergiofierro.posts_feed.repository.UserRepository
import kotlinx.coroutines.launch

class DetailViewModel @ViewModelInject constructor(
  private val postRepository: PostRepository,
  private val userRepository: UserRepository,
  private val commentRepository: CommentRepository
) : ViewModel() {

  @VisibleForTesting
  var _post: MutableLiveData<Post> = MutableLiveData()
  val user: LiveData<User?> = _post.switchMap { post ->
    viewModelScope.launch {
      userRepository.fetchUser(post.userId)
    }
    userRepository.observerUser(post.userId).map {
      mapUser(it)
    }
  }
  val comments: LiveData<List<Comment>?> = _post.switchMap { post ->
    viewModelScope.launch {
      commentRepository.fetchComments(post.id)
    }
    commentRepository.observerComments(post.id).map {
      mapComments(it)
    }
  }

  val isFavorite = ObservableBoolean(false)

  private fun mapComments(commentsResult: Result<List<Comment>>): List<Comment>? {
    return if (commentsResult is Success) {
      commentsResult.data
    } else {
      null
    }
  }

  private fun mapUser(userResult: Result<User?>): User? {
    return if (userResult is Success) {
      userResult.data
    } else {
      null
    }
  }

  fun setPost(post: Post) {
    _post.value = post
    isFavorite.set(post.favorite)
    viewModelScope.launch {
      postRepository.postRead(post)
    }
  }

  fun changeFavorite() {
    viewModelScope.launch {
      _post.value?.let {
        postRepository.changeFavorite(it)
        isFavorite.set(it.favorite)
      }
    }
  }
}
