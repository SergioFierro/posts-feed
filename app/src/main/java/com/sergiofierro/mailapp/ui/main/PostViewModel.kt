package com.sergiofierro.mailapp.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.sergiofierro.mailapp.data.Result
import com.sergiofierro.mailapp.data.Result.Success
import com.sergiofierro.mailapp.model.Post
import com.sergiofierro.mailapp.repository.PostRepository
import kotlinx.coroutines.launch

class PostViewModel @ViewModelInject constructor(
  private val postRepository: PostRepository
) : ViewModel() {

  private var list = ListType.ALL
  private val _posts: LiveData<List<Post>> = postRepository.observePosts().distinctUntilChanged().switchMap { filterPosts(it) }
  val posts: LiveData<List<Post>> = _posts

  private fun filterPosts(postsResult: Result<List<Post>>): LiveData<List<Post>> {
    val result = MutableLiveData<List<Post>>()
    if (postsResult is Success) {
      result.value = filterItems(postsResult.data)
    } else {
      result.value = emptyList()
    }

    return result
  }

  private fun filterItems(posts: List<Post>): List<Post> {
    val postsToShow = ArrayList<Post>()
    for (post in posts) {
      when (list) {
        ListType.ALL -> postsToShow.add(post)
        ListType.FAVORITES -> if (post.favorite) {
          postsToShow.add(post)
        }
      }
    }
    return postsToShow
  }

  fun setListType(listTypeType: ListType) {
    list = listTypeType
  }

  fun deletePost(post: Post) {
    viewModelScope.launch {
      postRepository.delete(post)
    }
  }
}
