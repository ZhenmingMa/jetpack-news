package com.ma.jetpack.news.data.api

import com.ma.jetpack.news.data.entity.BaseResponse
import com.ma.jetpack.news.data.entity.NewsDetail
import com.ma.jetpack.news.data.entity.NewsList
import com.ma.jetpack.news.data.entity.NewsType
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("/api/news/types")
    fun getTypes(): Call<BaseResponse<List<NewsType>>>

    @GET("/api/news/list")
    fun getNewsList(
        @Query("typeId") typeId: Int,
        @Query("page") page: Int
    ): Call<BaseResponse<List<NewsList>>>

    @GET("/api/news/details")
    fun getNewsDetail(@Query("newsId") newsId: String): Call<BaseResponse<NewsDetail>>
}