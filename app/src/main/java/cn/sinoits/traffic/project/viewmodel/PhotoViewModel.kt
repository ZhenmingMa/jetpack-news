package cn.sinoits.traffic.project.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.sinoits.traffic.project.data.respository.PhotoRespository

class PhotoViewModel(val photoRespository: PhotoRespository) : ViewModel() {

    val data =photoRespository.getData()
    val state = photoRespository.state
    fun refersh(){
        photoRespository.refresh()
    }
}
