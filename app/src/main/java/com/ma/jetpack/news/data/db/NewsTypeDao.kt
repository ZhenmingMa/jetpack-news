package com.ma.jetpack.news.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ma.jetpack.news.data.entity.NewsType

@Dao
interface NewsTypeDao {
    @Insert
    fun insert(list: List<NewsType>)

    @Query("select * from newstype")
    fun getAll(): List<NewsType>

    @Query("delete from newstype")
    fun deleteAll()

    @Query("select * from newstype where last_modifier >=:refreshTime")
    fun hasNewsType(refreshTime:Long):Boolean

}