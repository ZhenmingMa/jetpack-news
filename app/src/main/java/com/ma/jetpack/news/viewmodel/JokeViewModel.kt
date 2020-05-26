package com.ma.jetpack.news.viewmodel

import androidx.lifecycle.ViewModel
import com.ma.jetpack.news.data.respository.JokeRespository

class JokeViewModel(val respository: JokeRespository):ViewModel() {
    val data = respository.getData()
    val state = respository.state
    fun refersh(){
        respository.refresh()
    }

}