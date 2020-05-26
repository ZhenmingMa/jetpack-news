package cn.sinoits.traffic.project.viewmodel

import androidx.lifecycle.ViewModel
import cn.sinoits.traffic.project.data.respository.JokeRespository

class JokeViewModel(val respository: JokeRespository):ViewModel() {
    val data = respository.getData()
    val state = respository.state
    fun refersh(){
        respository.refresh()
    }

}