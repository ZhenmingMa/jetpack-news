package com.ma.jetpack.news.data.respository

import androidx.paging.PagedList
import com.ma.jetpack.news.data.api.PhotoService
import com.ma.jetpack.news.data.db.AppDatabase
import com.ma.jetpack.news.data.entity.BaseResponse
import com.ma.jetpack.news.data.entity.Photo
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