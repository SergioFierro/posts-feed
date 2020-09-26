package com.sergiofierro.mailapp.networking

import com.sergiofierro.mailapp.networking.api.UserAPI
import javax.inject.Inject

class UserClient @Inject constructor(private val userAPI: UserAPI) {
  suspend fun fetchUser(id: Int) = userAPI.fetchUser(id)
}
