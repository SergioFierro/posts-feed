package com.sergiofierro.mailapp.networking

import com.sergiofierro.mailapp.networking.api.PostAPI
import javax.inject.Inject

class PostClient @Inject constructor(private val postAPI: PostAPI) {

  suspend fun fetchPosts() = postAPI.fetchPosts()
}
