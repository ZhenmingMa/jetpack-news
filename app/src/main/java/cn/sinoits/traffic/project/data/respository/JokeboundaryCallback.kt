package cn.sinoits.traffic.project.data.respository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList.BoundaryCallback
import cn.sinoits.traffic.project.data.api.JokeService
import cn.sinoits.traffic.project.data.api.NetworkState
import cn.sinoits.traffic.project.data.db.JokeDao
import cn.sinoits.traffic.project.data.entity.BaseResponse
import cn.sinoits.traffic.project.data.entity.Joke
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