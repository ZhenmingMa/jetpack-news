package com.ma.jetpack.news.data.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ma.jetpack.news.data.entity.Photo

@Dao
interface PhotoDao{
    @Insert
    fun insert(list:List<Photo>)

    @Query("select * from photo" )
    fun getAll():DataSource.Factory<Int,Photo>

    @Query("delete from photo")
    fun deleteAll()
}