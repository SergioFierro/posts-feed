package com.sergiofierro.mailapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class User(
  @field:Json(name = "id") @PrimaryKey val id: Int,
  @field:Json(name = "name") val name: String,
  @field:Json(name = "email") val email: String,
  @field:Json(name = "phone") val phone: String,
  @field:Json(name = "website") val website: String
)
