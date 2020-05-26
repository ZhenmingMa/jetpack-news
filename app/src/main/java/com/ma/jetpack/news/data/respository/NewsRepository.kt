package com.ma.jetpack.news.data.respository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.paging.toLiveData
import com.ma.jetpack.news.data.api.NetBaseCallBack
import com.ma.jetpack.news.data.api.NetworkState
import com.ma.jetpack.news.data.api.NewsService
import com.ma.jetpack.news.data.db.AppDatabase
import com.ma.jetpack.news.data.entity.BaseResponse
import com.ma.jetpack.news.data.entity.NewsDetail
import com.ma.jetpack.news.data.entity.NewsList
import com.ma.jetpack.news.data.entity.NewsType
import retrofit2.Call
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class NewsRepository(
    private val api: NewsService,
    private val db: AppDatabase,
    private val io: Executor
) {
    var state = MutableLiveData<NetworkState>()
    fun getData():MutableLiveData<List<NewsType>> {
       val newsType = MutableLiveData<List<NewsType>>()
        io.execute {
            val hasNewsType =
                db.getNewsTypeDao().hasNewsType(System.currentTimeMillis() - FRESH_TIMEOUT)
            if (hasNewsType) {
                val data = db.getNewsTypeDao().getAll()
                newsType.postValue(data)
            } else {
                api.getTypes().enqueue(
                    object : NetBaseCallBack<List<NewsType>>(state) {
                        override fun onResponseHandle(
                            call: Call<BaseResponse<List<NewsType>>>,
                            data: List<NewsType>
                        ) {
                            data.map {
                                it.lastModifier = System.currentTimeMillis()
                            }
                            io.execute {
                                db.runInTransaction {
                                    db.getNewsTypeDao().deleteAll()
                                    db.getNewsTypeDao().insert(data)
                                }
                            }
                            newsType.postValue(data)
                        }
                    }
                )
            }
        }
        return newsType
    }


    fun getNewsList(type: Int): Listing<NewsList> {
        val sourceFactory = NewsListDataSourceFactory(api, type)
        val toLiveData = sourceFactory.toLiveData(
            pageSize = 10
        )
        val refreshState = sourceFactory.sourceLiveData.switchMap {
            it.initialLoad
        }
        return Listing(
            pagedList = toLiveData,
            refreshState = refreshState,
            networkState = sourceFactory.sourceLiveData.switchMap { it.netWorkState },
            refresh = { sourceFactory.sourceLiveData.value?.invalidate() }
        )
    }


    fun getNewsDetail(
        newsId: String,
        newsDetail: MutableLiveData<NewsDetail>,
        state: MutableLiveData<NetworkState>
    ) {
        api.getNewsDetail(newsId)
            .enqueue(
                object : NetBaseCallBack<NewsDetail>(state) {
                    override fun onResponseHandle(
                        call: Call<BaseResponse<NewsDetail>>,
                        data: NewsDetail
                    ) {
                        newsDetail.postValue(data)
                    }

                }
            )
    }

    companion object {
        val FRESH_TIMEOUT = TimeUnit.DAYS.toMillis(1)
    }

}