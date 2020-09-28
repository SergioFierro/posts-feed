package com.sergiofierro.posts_feed.networking

import com.sergiofierro.posts_feed.networking.api.UserApi
import javax.inject.Inject

class UserClient @Inject constructor(private val userApi: UserApi) {
  suspend fun fetchUser(id: Int) = userApi.fetchUser(id)
}
