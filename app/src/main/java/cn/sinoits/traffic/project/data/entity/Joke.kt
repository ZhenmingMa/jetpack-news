package cn.sinoits.traffic.project.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import java.sql.Date

@Entity
data class Joke(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @field:SerializedName("updateTime")
    val updateTime: String? = null,

    @field:SerializedName("content")
    val content: String? = null,

    @ColumnInfo(name = "last_modified" )
    var lastModified: Long?
)