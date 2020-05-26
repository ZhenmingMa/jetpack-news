package com.ma.jetpack.news.data.api

import com.ma.jetpack.news.data.entity.BaseResponse
import com.ma.jetpack.news.data.entity.PageInfo
import com.ma.jetpack.news.data.entity.Photo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoService {
    @GET("/api/image/girl/list")
    fun getImages(@Query("page") page: Int): Call<BaseResponse<PageInfo<Photo>>>

    @GET("/api/image/girl/list/random")
    fun getImages(): Call<BaseResponse<List<Photo>>>
}