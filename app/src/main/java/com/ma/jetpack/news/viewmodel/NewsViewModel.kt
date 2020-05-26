package com.ma.jetpack.news.viewmodel

import androidx.lifecycle.ViewModel
import com.ma.jetpack.news.data.respository.NewsRepository

class NewsViewModel(newsRepository: NewsRepository) : ViewModel() {
    val newsTypes = newsRepository.getData()
}