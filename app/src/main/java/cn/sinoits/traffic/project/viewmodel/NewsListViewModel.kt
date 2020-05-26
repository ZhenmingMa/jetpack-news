package cn.sinoits.traffic.project.viewmodel

import androidx.lifecycle.ViewModel
import cn.sinoits.traffic.project.data.respository.NewsRepository

class NewsListViewModel(val newsRepository: NewsRepository,val typeId: Int) : ViewModel() {
    var listing = newsRepository.getNewsList(typeId)

    var state = listing.refreshState
}
