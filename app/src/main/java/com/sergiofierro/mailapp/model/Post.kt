package com.sergiofierro.mailapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
@JsonClass(generateAdapter = true)
data class Post(
  @field:Json(name = "id") @PrimaryKey val id: Int,
  @field:Json(name = "userId") val userId: Int,
  @field:Json(name = "title") val title: String,
  @field:Json(name = "body") val body: String,
  var favorite: Boolean = false,
  var unread: Boolean = true
): Parcelable
