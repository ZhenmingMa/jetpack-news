package com.ma.jetpack.news.brige

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ma.jetpack.news.SPTag
import com.blankj.utilcode.util.SPUtils

class ShareViewModel :ViewModel(){

    //保存recycleview的状态 有application管理
    var photoCurIndex = MutableLiveData<Int>()
    var photoOffset = MutableLiveData<Int>()

    var jokeCurIndex = ObservableField<Int>()
    var jokeOffset = ObservableField<Int>()

    //保存新闻首页tab和位置
    var newsTypeCurIndex = MutableLiveData<Int>().apply {
        //sp存储，退出程序也能保存状态
       value = SPUtils.getInstance().getInt(SPTag.newsTypeCurIndex,0)
    }

}