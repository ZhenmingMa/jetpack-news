package com.ma.jetpack.news.viewmodel

import androidx.lifecycle.ViewModel
import com.ma.jetpack.news.data.respository.PhotoRespository

class PhotoViewModel(val photoRespository: PhotoRespository) : ViewModel() {

    val data =photoRespository.getData()
    val state = photoRespository.state
    fun refersh(){
        photoRespository.refresh()
    }
}
