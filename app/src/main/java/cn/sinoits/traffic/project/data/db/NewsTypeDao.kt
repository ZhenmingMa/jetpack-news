package cn.sinoits.traffic.project.data.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import cn.sinoits.traffic.project.data.entity.NewsType
import java.lang.reflect.Type

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