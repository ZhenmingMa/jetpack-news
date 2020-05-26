package cn.sinoits.traffic.project.page

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import cn.sinoits.traffic.project.R
import cn.sinoits.traffic.project.SPTag
import cn.sinoits.traffic.project.data.api.NetClient
import cn.sinoits.traffic.project.data.api.NewsService
import cn.sinoits.traffic.project.data.db.AppDatabase
import cn.sinoits.traffic.project.data.respository.NewsRepository
import cn.sinoits.traffic.project.page.adapter.NewsVPAdapter
import cn.sinoits.traffic.project.page.base.BaseFragment
import cn.sinoits.traffic.project.viewmodel.NewsViewModel
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