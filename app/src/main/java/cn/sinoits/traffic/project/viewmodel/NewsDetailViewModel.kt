package cn.sinoits.traffic.project.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.sinoits.traffic.project.data.api.NetworkState
import cn.sinoits.traffic.project.data.entity.NewsDetail
import cn.sinoits.traffic.project.data.respository.NewsRepository

class NewsDetailViewModel(private val newsRepository: NewsRepository):ViewModel() {
    val newsDetail = MutableLiveData<NewsDetail>()
    var state = MutableLiveData<NetworkState>()
    fun getData(newsId: String){
        newsRepository.getNewsDetail(newsId,newsDetail,state)
    }
}