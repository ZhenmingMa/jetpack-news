package cn.sinoits.traffic.project.data.respository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import cn.sinoits.traffic.project.data.api.NetBaseCallBack
import cn.sinoits.traffic.project.data.api.NetworkState
import cn.sinoits.traffic.project.data.api.PhotoService
import cn.sinoits.traffic.project.data.db.AppDatabase
import cn.sinoits.traffic.project.data.entity.BaseResponse
import cn.sinoits.traffic.project.data.entity.PageInfo
import cn.sinoits.traffic.project.data.entity.Photo
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