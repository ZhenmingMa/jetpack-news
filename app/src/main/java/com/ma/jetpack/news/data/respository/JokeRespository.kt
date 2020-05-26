package com.ma.jetpack.news.data.respository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.ma.jetpack.news.data.api.JokeService
import com.ma.jetpack.news.data.api.NetBaseCallBack
import com.ma.jetpack.news.data.api.NetworkState
import com.ma.jetpack.news.data.db.AppDatabase
import com.ma.jetpack.news.data.entity.BaseResponse
import com.ma.jetpack.news.data.entity.Joke
import retrofit2.Call
import java.util.concurrent.Executor

class JokeRespository(val db: AppDatabase, val api: JokeService,val io:Executor) {

    val state =MutableLiveData<NetworkState>()

    fun getData(): LiveData<PagedList<Joke>> {
        val all = db.getJokeDao().getAll()

        return all.toLiveData(
            pageSize = 50,
            boundaryCallback = JokeboundaryCallback(api, db.getJokeDao(), io)
        )
    }

    fun refresh() {
        api.getJokesRandom().enqueue(
           object :NetBaseCallBack<List<Joke>>(state){
               override fun onResponseHandle(
                   call: Call<BaseResponse<List<Joke>>>,
                   data: List<Joke>
               ) {
                   val map = data.map {
                       it.apply {
                           lastModified = System.currentTimeMillis()
                       }
                   }
                   io.execute {
                       db.runInTransaction(){
                           db.getJokeDao().delete()
                           db.getJokeDao().insert(map)
                       }
                   }
               }
           }
        )
    }

}
