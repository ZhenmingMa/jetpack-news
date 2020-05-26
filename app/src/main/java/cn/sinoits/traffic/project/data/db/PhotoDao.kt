package cn.sinoits.traffic.project.data.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import cn.sinoits.traffic.project.data.entity.Photo

@Dao
interface PhotoDao{
    @Insert
    fun insert(list:List<Photo>)

    @Query("select * from photo" )
    fun getAll():DataSource.Factory<Int,Photo>

    @Query("delete from photo")
    fun deleteAll()
}