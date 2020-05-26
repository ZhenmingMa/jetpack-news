package cn.sinoits.traffic.project.data.respository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import cn.sinoits.traffic.project.data.api.NewsService
import cn.sinoits.traffic.project.data.entity.NewsList

class NewsListDataSourceFactory(val api: NewsService, val typeId: Int) :
    DataSource.Factory<Int, NewsList>() {
    val sourceLiveData = MutableLiveData<NewsListDataSource>()
    override fun create(): DataSource<Int, NewsList> {
        val dataSource = NewsListDataSource(api, typeId)
        sourceLiveData.postValue(dataSource)
        return dataSource
    }
}