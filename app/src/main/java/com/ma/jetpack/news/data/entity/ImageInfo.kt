package com.ma.jetpack.news.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity()
data class Photo(
    @PrimaryKey(autoGenerate = true) var id:Int,
    @SerializedName("imageUrl") var imageUrl: String,
    @SerializedName("imageSize") var imageSize: String,
    @SerializedName("imageFileLength") var imageFileLength: String
)