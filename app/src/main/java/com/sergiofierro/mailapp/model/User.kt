package com.sergiofierro.mailapp.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
  @field:Json(name = "id") val id: Int,
  @field:Json(name = "name") val name: String,
  @field:Json(name = "email") val email: String,
  @field:Json(name = "phone") val phone: String,
  @field:Json(name = "website") val website: String
)
