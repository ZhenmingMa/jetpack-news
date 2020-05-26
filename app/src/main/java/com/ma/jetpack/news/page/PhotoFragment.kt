package com.ma.jetpack.news.page

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ma.jetpack.news.R
import com.ma.jetpack.news.data.api.NetClient
import com.ma.jetpack.news.data.api.NetworkState
import com.ma.jetpack.news.data.api.PhotoService
import com.ma.jetpack.news.data.db.AppDatabase
import com.ma.jetpack.news.data.respository.PhotoRespository
import com.ma.jetpack.news.page.base.BaseFragment
import com.ma.jetpack.news.page.adapter.PhotoRvAdapter
import com.ma.jetpack.news.viewmodel.PhotoViewModel
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
