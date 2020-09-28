package com.sergiofierro.posts_feed.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergiofierro.posts_feed.repository.PostRepository
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
  private val postRepository: PostRepository
) : ViewModel() {

  fun refresh() {
    viewModelScope.launch {
      postRepository.fetchAll()
    }
  }


  fun deleteAll() {
    viewModelScope.launch {
      postRepository.deleteAll()
    }
  }
}
