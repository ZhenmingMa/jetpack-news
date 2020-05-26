package cn.sinoits.traffic.project.page

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.sinoits.traffic.project.R
import cn.sinoits.traffic.project.data.api.NetClient
import cn.sinoits.traffic.project.data.api.NetworkState
import cn.sinoits.traffic.project.data.api.PhotoService
import cn.sinoits.traffic.project.data.db.AppDatabase
import cn.sinoits.traffic.project.data.respository.PhotoRespository
import cn.sinoits.traffic.project.page.base.BaseFragment
import cn.sinoits.traffic.project.page.adapter.PhotoRvAdapter
import cn.sinoits.traffic.project.viewmodel.PhotoViewModel
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.fragment_photo.*
import java.util.concurrent.Executors

class PhotoFragment : BaseFragment() {

    companion object {
        fun newInstance() = PhotoFragment()
    }

    override fun layoutRes(): Int =R.layout.fragment_photo

    val model: PhotoViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return PhotoViewModel(
                    photoRespository = PhotoRespository(
                        AppDatabase.getInstance(context),
                        NetClient.create(PhotoService::class.java),
                        Executors.newFixedThreadPool(1)
                    )
                ) as T
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = PhotoRvAdapter()

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//        LinearSnapHelper().attachToRecyclerView(rv)
        rv.layoutManager = layoutManager
        rv.adapter = adapter

        model.data.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            layoutManager.scrollToPositionWithOffset(shareViewModel?.photoCurIndex?.value?:0,shareViewModel?.photoOffset?.value?:0)

        })

        rv.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val topView = layoutManager.getChildAt(0)!!
                val curIndex = layoutManager.getPosition(topView)
                val offset = topView.top
                shareViewModel?.photoCurIndex?.postValue(curIndex)
                shareViewModel?.photoOffset?.postValue(offset)
            }
        })

        model.state.observe(viewLifecycleOwner, Observer {
            swipe_layout.isRefreshing = when (it) {
                NetworkState.LOADED -> false
                NetworkState.LOADING -> false
                else -> false
            }
            it.msg?.let {
                ToastUtils.showShort(it)
            }
        })
        swipe_layout.setOnRefreshListener {
            model.refersh()

        }
    }

}
