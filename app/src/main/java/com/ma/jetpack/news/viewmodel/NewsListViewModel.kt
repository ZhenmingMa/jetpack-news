package com.ma.jetpack.news.viewmodel

import androidx.lifecycle.ViewModel
import com.ma.jetpack.news.data.respository.NewsRepository

class NewsListViewModel(val newsRepository: NewsRepository,val typeId: Int) : ViewModel() {
    var listing = newsRepository.getNewsList(typeId)

    var state = listing.refreshState
}
