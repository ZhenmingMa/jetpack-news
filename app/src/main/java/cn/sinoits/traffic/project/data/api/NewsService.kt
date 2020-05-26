package cn.sinoits.traffic.project.data.api

import cn.sinoits.traffic.project.data.entity.BaseResponse
import cn.sinoits.traffic.project.data.entity.NewsDetail
import cn.sinoits.traffic.project.data.entity.NewsList
import cn.sinoits.traffic.project.data.entity.NewsType
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