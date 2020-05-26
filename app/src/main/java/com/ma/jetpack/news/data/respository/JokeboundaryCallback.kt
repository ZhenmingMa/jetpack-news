package com.ma.jetpack.news.data.respository

import androidx.paging.PagedList.BoundaryCallback
import com.ma.jetpack.news.data.api.JokeService
import com.ma.jetpack.news.data.db.JokeDao
import com.ma.jetpack.news.data.entity.BaseResponse
import com.ma.jetpack.news.data.entity.Joke
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor

class JokeboundaryCallback(val api: JokeService, val dao: JokeDao,val ioExecutor: Executor) :
    BoundaryCallback<Joke>() {
    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        requestAndSaveData()
    }

    override fun onItemAtEndLoaded(itemAtEnd: Joke) {
        requestAndSaveData()
    }

    fun requestAndSaveData() {

        val callback = object : Callback<BaseResponse<List<Joke>>> {
            override fun onFailure(call: Call<BaseResponse<List<Joke>>>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<BaseResponse<List<Joke>>>,
                response: Response<BaseResponse<List<Joke>>>
            ) {
                val data = response.body()?.data!!
                val map = data.map {
                    it.apply {
                        lastModified = System.currentTimeMillis()
                    }
                }
                if (response.isSuccessful) {
                    ioExecutor.execute {
                        dao.insert(map)
                    }
                }
            }
        }
        api.getJokesRandom()
            .enqueue(callback)
    }
}