package com.ma.jetpack.news.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class NewsType(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @field:SerializedName("typeId")
    var typeId: Int,
    @field:SerializedName("typeName")
    var typeName: String,
    @ColumnInfo(name = "last_modifier")
    var lastModifier: Long

)
