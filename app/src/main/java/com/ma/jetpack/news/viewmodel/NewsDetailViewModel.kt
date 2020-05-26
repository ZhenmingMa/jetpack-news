package com.ma.jetpack.news.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ma.jetpack.news.data.api.NetworkState
import com.ma.jetpack.news.data.entity.NewsDetail
import com.ma.jetpack.news.data.respository.NewsRepository

class NewsDetailViewModel(private val newsRepository: NewsRepository):ViewModel() {
    val newsDetail = MutableLiveData<NewsDetail>()
    var state = MutableLiveData<NetworkState>()
    fun getData(newsId: String){
        newsRepository.getNewsDetail(newsId,newsDetail,state)
    }
}