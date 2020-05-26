package cn.sinoits.traffic.project.data.respository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import cn.sinoits.traffic.project.data.api.NetBaseCallBack
import cn.sinoits.traffic.project.data.api.NetworkState
import cn.sinoits.traffic.project.data.api.NewsService
import cn.sinoits.traffic.project.data.entity.BaseResponse
import cn.sinoits.traffic.project.data.entity.NewsList
import com.blankj.utilcode.util.TimeUtils
import retrofit2.Call

class NewsListDataSource(private val api: NewsService, private val typeId: Int) :
    PageKeyedDataSource<Int, NewsList>() {

    val netWorkState = MutableLiveData<NetworkState>()
    val initialLoad = MutableLiveData<NetworkState>()


    val list = mutableListOf<NewsList>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, NewsList>
    ) {
        initialLoad.postValue(NetworkState.LOADING)
        netWorkState.postValue(NetworkState.LOADING)
        api.getNewsList(typeId, 1).enqueue(
            object : NetBaseCallBack<List<NewsList>>(initialLoad) {
                override fun onResponseHandle(
                    call: Call<BaseResponse<List<NewsList>>>,
                    data: List<NewsList>
                ) {
                    var filter = listOf<NewsList>()

                    if (typeId == 522 || typeId == 526) {
                        filter = data.filterNot {
                            it.videoList.isNullOrEmpty()
                        }
                        //处理时间戳
                        filter = filter.map {

                            it.apply {
                                var time = 0L
                                try {
                                    time = postTime?.toLong() ?: 0
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    time = 0
                                }
                                postTime = TimeUtils.millis2String(time)
                            }
                        }
                    } else {
                        filter = data.filterNot {
                            it.newsId?.length == 9
                        }
                        filter = filter.filterNot {
                            filter(it)
                        }
                        list.addAll(filter)
                    }

                    callback.onResult(filter, 0, 2)
                    initialLoad.postValue(NetworkState.LOADED)
                    netWorkState.postValue(NetworkState.LOADED)
                }
            }
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, NewsList>) {
        netWorkState.postValue(NetworkState.LOADING)
        api.getNewsList(typeId, params.key).enqueue(
            object : NetBaseCallBack<List<NewsList>>(netWorkState) {
                override fun onResponseHandle(
                    call: Call<BaseResponse<List<NewsList>>>,
                    data: List<NewsList>
                ) {
                    var filter = listOf<NewsList>()

                    if (typeId == 522 || typeId == 526) {
                        filter = data.filterNot {
                            it.videoList.isNullOrEmpty()
                        }
                        //处理时间戳
                        filter = filter.map {
                            it.apply { postTime = TimeUtils.millis2String(postTime?.toLong() ?: 0) }
                        }

                    } else {
                        filter = data.filter {
                            it.newsId?.length == 16
                        }
                        filter = filter.filterNot {
                            filter(it)
                        }
                        list.addAll(filter)
                    }

                    callback.onResult(filter, params.key + 1)
                    netWorkState.postValue(NetworkState.LOADED)
                }
            }
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, NewsList>) {

    }

    fun filter(newsList: NewsList): Boolean {
        for (e in list) {
            if (e.newsId == newsList.newsId) {
                return true
            }
        }
        return false
    }

}