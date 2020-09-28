package com.sergiofierro.mailapp.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergiofierro.mailapp.repository.PostRepository
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
