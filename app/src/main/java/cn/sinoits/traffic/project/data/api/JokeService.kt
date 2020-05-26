package cn.sinoits.traffic.project.data.api

import cn.sinoits.traffic.project.data.entity.BaseResponse
import cn.sinoits.traffic.project.data.entity.Joke
import cn.sinoits.traffic.project.data.entity.PageInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface JokeService {
    @GET("/api/jokes/list")
    fun getJoke(@Query("page") page: Int): Call<BaseResponse<PageInfo<Joke>>>
    @GET("/api/jokes/list/random")
    fun getJokesRandom(): Call<BaseResponse<List<Joke>>>
}