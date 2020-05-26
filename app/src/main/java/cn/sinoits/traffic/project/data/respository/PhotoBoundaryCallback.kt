package cn.sinoits.traffic.project.data.respository

import androidx.paging.PagedList
import cn.sinoits.traffic.project.data.api.NetBaseCallBack
import cn.sinoits.traffic.project.data.api.NetworkState
import cn.sinoits.traffic.project.data.api.PhotoService
import cn.sinoits.traffic.project.data.db.AppDatabase
import cn.sinoits.traffic.project.data.entity.BaseResponse
import cn.sinoits.traffic.project.data.entity.PageInfo
import cn.sinoits.traffic.project.data.entity.Photo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor

class PhotoBoundaryCallback(
    private val db: AppDatabase,
    private val api: PhotoService,
    private val io: Executor
) : PagedList.BoundaryCallback<Photo>() {
    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        saveAndRequest()
    }

    override fun onItemAtEndLoaded(itemAtEnd: Photo) {
        super.onItemAtEndLoaded(itemAtEnd)
        saveAndRequest()
    }
    var page = 1;
    fun saveAndRequest(){
        api.getImages().enqueue(
            object:Callback<BaseResponse<List<Photo>>>{
                override fun onFailure(call: Call<BaseResponse<List<Photo>>>, t: Throwable) {

                }
                override fun onResponse(
                    call: Call<BaseResponse<List<Photo>>>,
                    response: Response<BaseResponse<List<Photo>>>
                ) {
                    if (response.isSuccessful){
                        page++
                        io.execute {
                            db.getPhotoDao().insert(response.body()?.data!!)
                        }
                    }
                }

            }
        )
    }
}