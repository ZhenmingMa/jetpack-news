package com.ma.jetpack.news.data.api

import com.ma.jetpack.news.data.entity.BaseResponse
import com.ma.jetpack.news.data.entity.Joke
import com.ma.jetpack.news.data.entity.PageInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface JokeService {
    @GET("/api/jokes/list")
    fun getJoke(@Query("page") page: Int): Call<BaseResponse<PageInfo<Joke>>>
    @GET("/api/jokes/list/random")
    fun getJokesRandom(): Call<BaseResponse<List<Joke>>>
}