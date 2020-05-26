package cn.sinoits.traffic.project.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.sinoits.traffic.project.brige.ShareViewModel
import cn.sinoits.traffic.project.data.api.NetClient
import cn.sinoits.traffic.project.data.api.NewsService
import cn.sinoits.traffic.project.data.db.AppDatabase
import cn.sinoits.traffic.project.data.entity.NewsType
import cn.sinoits.traffic.project.data.respository.NewsRepository
import java.util.concurrent.Executors

class NewsViewModel(newsRepository: NewsRepository) : ViewModel() {
    val newsTypes = newsRepository.getData()
}