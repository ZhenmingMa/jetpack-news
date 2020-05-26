package com.ma.jetpack.news.data.respository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.ma.jetpack.news.data.api.NetBaseCallBack
import com.ma.jetpack.news.data.api.NetworkState
import com.ma.jetpack.news.data.api.PhotoService
import com.ma.jetpack.news.data.db.AppDatabase
import com.ma.jetpack.news.data.entity.BaseResponse
import com.ma.jetpack.news.data.entity.Photo
import retrofit2.Call
import java.util.concurrent.Executor

class PhotoRespository(
    private val db: AppDatabase,
    private val api: PhotoService,
    private val io: Executor
) {

    val state = MutableLiveData<NetworkState>()

    fun getData(): LiveData<PagedList<Photo>> {
        return db.getPhotoDao().getAll().toLiveData(
            50, boundaryCallback =
            PhotoBoundaryCallback(db, api, io)
        )
    }

    fun refresh() {
        api.getImages().enqueue(
            object : NetBaseCallBack<List<Photo>>(state) {
                override fun onResponseHandle(
                    call: Call<BaseResponse<List<Photo>>>,
                    data: List<Photo>
                ) {
                    io.execute {
                        db.runInTransaction {
                            db.getPhotoDao().deleteAll()
                            db.getPhotoDao().insert(data)
                        }
                    }
                }
            }
        )
    }

}