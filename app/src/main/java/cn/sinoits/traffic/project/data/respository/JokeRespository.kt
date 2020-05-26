package cn.sinoits.traffic.project.data.respository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import cn.sinoits.traffic.project.data.api.JokeService
import cn.sinoits.traffic.project.data.api.NetBaseCallBack
import cn.sinoits.traffic.project.data.api.NetworkState
import cn.sinoits.traffic.project.data.db.AppDatabase
import cn.sinoits.traffic.project.data.db.JokeDao
import cn.sinoits.traffic.project.data.entity.BaseResponse
import cn.sinoits.traffic.project.data.entity.Joke
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

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
