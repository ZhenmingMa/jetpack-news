package com.ma.jetpack.news.data.respository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.ma.jetpack.news.data.api.NewsService
import com.ma.jetpack.news.data.entity.NewsList

class NewsListDataSourceFactory(val api: NewsService, val typeId: Int) :
    DataSource.Factory<Int, NewsList>() {
    val sourceLiveData = MutableLiveData<NewsListDataSource>()
    override fun create(): DataSource<Int, NewsList> {
        val dataSource = NewsListDataSource(api, typeId)
        sourceLiveData.postValue(dataSource)
        return dataSource
    }
}