package com.ma.jetpack.news.page

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.ma.jetpack.news.R
import com.ma.jetpack.news.SPTag
import com.ma.jetpack.news.data.api.NetClient
import com.ma.jetpack.news.data.api.NewsService
import com.ma.jetpack.news.data.db.AppDatabase
import com.ma.jetpack.news.data.respository.NewsRepository
import com.ma.jetpack.news.page.adapter.NewsVPAdapter
import com.ma.jetpack.news.page.base.BaseFragment
import com.ma.jetpack.news.viewmodel.NewsViewModel
import com.blankj.utilcode.util.SPUtils
import kotlinx.android.synthetic.main.fragment_news.*
import java.util.concurrent.Executors

class NewsFragment : BaseFragment() {
    override fun layoutRes(): Int {
        return R.layout.fragment_news
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tab_layout.setupWithViewPager(view_pager)
        val adapter = NewsVPAdapter(childFragmentManager)
        view_pager.adapter = adapter

        val model: NewsViewModel by viewModels {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return NewsViewModel(
                        NewsRepository(
                            NetClient.create(NewsService::class.java),
                            AppDatabase.getInstance(context),
                            Executors.newFixedThreadPool(1)
                        )
                    ) as T
                }

            }
        }
        model.newsTypes.observe(viewLifecycleOwner, Observer {
            adapter.list = it
            adapter.notifyDataSetChanged()
            //刷新之后恢复tablayout的位置
            view_pager.currentItem = shareViewModel?.newsTypeCurIndex?.value ?: 0
            tab_layout.setScrollPosition(shareViewModel?.newsTypeCurIndex?.value ?: 0, 0f, false)

        })

        view_pager.addOnPageChangeListener(
            object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    shareViewModel?.newsTypeCurIndex?.value = position
                    SPUtils.getInstance().put(SPTag.newsTypeCurIndex, position)
                }
            }
        )
    }

    override fun onResume() {
        super.onResume()
    }
}