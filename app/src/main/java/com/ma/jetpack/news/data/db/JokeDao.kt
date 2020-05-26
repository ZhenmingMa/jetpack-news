package com.ma.jetpack.news.data.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ma.jetpack.news.data.entity.Joke

@Dao
interface JokeDao {

    @Insert()
    fun insert(list: List<Joke>)

    @Query("select * from joke")
    fun getAll(): DataSource.Factory<Int, Joke>

    @Query("delete from joke")
    fun delete()

}