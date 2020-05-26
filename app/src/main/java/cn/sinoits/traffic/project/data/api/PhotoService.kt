package cn.sinoits.traffic.project.data.api

import cn.sinoits.traffic.project.data.entity.BaseResponse
import cn.sinoits.traffic.project.data.entity.PageInfo
import cn.sinoits.traffic.project.data.entity.Photo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoService {
    @GET("/api/image/girl/list")
    fun getImages(@Query("page") page: Int): Call<BaseResponse<PageInfo<Photo>>>

    @GET("/api/image/girl/list/random")
    fun getImages(): Call<BaseResponse<List<Photo>>>
}