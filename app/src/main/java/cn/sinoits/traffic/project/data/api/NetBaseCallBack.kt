package cn.sinoits.traffic.project.data.api

import androidx.lifecycle.MutableLiveData
import cn.sinoits.traffic.project.data.entity.BaseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class NetBaseCallBack<T>(var state: MutableLiveData<NetworkState>?) :Callback<BaseResponse<T>>{
    init {
        state?.postValue(NetworkState.LOADING)
    }

    abstract fun onResponseHandle(call: Call<BaseResponse<T>>, data: T)

    override fun onFailure(call: Call<BaseResponse<T>>, t: Throwable) {
        state?.postValue(NetworkState.error(t.message?:"未知错误"))
    }

    override fun onResponse(call: Call<BaseResponse<T>>, response: Response<BaseResponse<T>>) {

        if (response.isSuccessful){
           response.body()?.let {
               if (it.code ==1){
                   state?.postValue(NetworkState.LOADED)
                    onResponseHandle(call,response.body()?.data!!)
               }else{
                   state?.postValue(NetworkState.error(response.body()?.msg?:"未知错误"))
               }
           }?:let {  state?.postValue(NetworkState.error("未知错误")) }

        }else{
            state?.postValue(NetworkState.error("error code:${response.code()}"))
        }
    }


}