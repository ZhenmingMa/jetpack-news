package cn.sinoits.traffic.project.page

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.sinoits.traffic.project.GlideApp
import cn.sinoits.traffic.project.R
import cn.sinoits.traffic.project.data.api.NetClient
import cn.sinoits.traffic.project.data.api.NewsService
import cn.sinoits.traffic.project.data.db.AppDatabase
import cn.sinoits.traffic.project.data.respository.NewsRepository
import cn.sinoits.traffic.project.databinding.FragmentNewsListBinding
import cn.sinoits.traffic.project.page.adapter.NewsListRvAdapter
import cn.sinoits.traffic.project.page.base.BaseFragment
import cn.sinoits.traffic.project.page.view.SampleCoverVideo
import cn.sinoits.traffic.project.viewmodel.NewsListViewModel
import com.shuyu.gsyvideoplayer.GSYVideoManager
import kotlinx.android.synthetic.main.fragment_news_list.*
import java.util.concurrent.Executors


class NewsListFragment : BaseFragment() {
    override fun layoutRes(): Int {
        return R.layout.fragment_news_list
    }

    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bind = FragmentNewsListBinding.bind(view)
        val type = arguments?.getInt("id")
        val model: NewsListViewModel by viewModels {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return NewsListViewModel(
                        NewsRepository(
                            NetClient.create(NewsService::class.java),
                            AppDatabase.getInstance(context),
                            Executors.newFixedThreadPool(1)
                        ), type!!
                    ) as T
                }
            }
        }

        bind.model = model
        bind.lifecycleOwner = this

        //分割线
//        val value = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
//        rv.addItemDecoration(value)
        val linearLayoutManager = LinearLayoutManager(context)
        rv.layoutManager = linearLayoutManager
        val adapter = NewsListRvAdapter()
        rv.adapter = adapter
//        if (type == 522 || type == 526)
//            LinearSnapHelper()
//                .attachToRecyclerView(rv)

        var isScrolling = false
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var firstVisibleItem = 0
            var lastVisibleItem: Int = 0
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_SETTLING) {
                    isScrolling = true
                    GlideApp.with(context!!).pauseRequests()
                } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (isScrolling) {
                        GlideApp.with(context!!).resumeRequests()
                    }
                    isScrolling = false
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                //自动播放
                if (linearLayoutManager.childCount >= 1) {
                    val centerView = linearLayoutManager.getChildAt(1)
                    centerView?.let {
                        rv?.let {
                            if (centerView.top > rv.height / 10 * 1 && centerView.top < rv.height / 10 * 5
                            ) {
                                val detail_player =
                                    centerView.findViewById<SampleCoverVideo>(R.id.detail_player)
                                if (!detail_player.isInPlayingState) {
                                    detail_player?.startPlayLogic()
                                } else {
                                    when (detail_player.currentState) {
                                        5 -> detail_player?.onVideoResume(true)
                                    }
                                }

                            }
                        }

                    }
                }

                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition()
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                //大于0说明有播放
                if (GSYVideoManager.instance().playPosition >= 0) {
                    //当前播放的位置
                    val position = GSYVideoManager.instance().playPosition
                    //对应的播放列表TAG
                    if (GSYVideoManager.instance()
                            .playTag == NewsListRvAdapter.Holder.TAG && (position < firstVisibleItem || position > lastVisibleItem)
                    ) {
                        //如果滑出去了上面和下面就是否，和今日头条一样
                        //是否全屏
                        if (!GSYVideoManager.isFullState(this@NewsListFragment.mActivity)) {
                            GSYVideoManager.releaseAllVideos()
//                            GSYVideoManager.onPause()
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        })

        swipe_layout.setOnRefreshListener {
            model.listing.refresh()
        }

        model.listing.pagedList
            .observe(viewLifecycleOwner, Observer {
                adapter.submitList(it)
            })

    }

    override fun onResume() {
        super.onResume()
        GSYVideoManager.onResume(false)
    }

    override fun onPause() {
        super.onPause()
        GSYVideoManager.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
    }


}

