package cn.sinoits.traffic.project.page

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.sinoits.traffic.project.R
import cn.sinoits.traffic.project.data.api.JokeService
import cn.sinoits.traffic.project.data.api.NetClient
import cn.sinoits.traffic.project.data.api.Status
import cn.sinoits.traffic.project.data.db.AppDatabase
import cn.sinoits.traffic.project.data.entity.Joke
import cn.sinoits.traffic.project.data.respository.JokeRespository
import cn.sinoits.traffic.project.page.adapter.JokeRvAdapter
import cn.sinoits.traffic.project.page.base.BaseFragment
import cn.sinoits.traffic.project.viewmodel.JokeViewModel
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.fragment_joke.*
import java.util.concurrent.Executors

class JokeFragment : BaseFragment() {
    override fun layoutRes(): Int {
        return R.layout.fragment_joke
    }

    val model: JokeViewModel by viewModels {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return JokeViewModel(
                    JokeRespository(
                        AppDatabase.getInstance(context),
                        NetClient.create(JokeService::class.java),
                        Executors.newFixedThreadPool(1)
                    )
                ) as T
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = JokeRvAdapter()
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv.layoutManager = linearLayoutManager
        rv.adapter = adapter

        model.data.observe(viewLifecycleOwner,
            Observer<PagedList<Joke>> { t ->
                adapter.submitList(t)
                linearLayoutManager.scrollToPositionWithOffset(
                    shareViewModel?.jokeCurIndex?.get() ?: 0, shareViewModel?.jokeOffset?.get() ?: 0
                )
            })

        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val topView = linearLayoutManager.getChildAt(0)!!
                val position = linearLayoutManager.getPosition(topView)
                shareViewModel?.jokeCurIndex?.set(position)
                shareViewModel?.jokeOffset?.set(topView.top)
            }
        })

        swipe_layout.setOnRefreshListener {
            model.refersh()
        }
        model.state.observe(viewLifecycleOwner, Observer {
            swipe_layout.isRefreshing = when (it.status) {
                Status.RUNNING -> true
                Status.FAILED -> false
                Status.SUCCESS -> false
            }
            it.msg?.let { ToastUtils.showShort(it) }
        })


    }

}