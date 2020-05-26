package com.ma.jetpack.news.page

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.ma.jetpack.news.R
import com.ma.jetpack.news.data.api.NetClient
import com.ma.jetpack.news.data.api.NewsService
import com.ma.jetpack.news.data.db.AppDatabase
import com.ma.jetpack.news.data.respository.NewsRepository
import com.ma.jetpack.news.databinding.FragmentNewsDetailBinding
import com.ma.jetpack.news.page.base.BaseFragment
import com.ma.jetpack.news.viewmodel.NewsDetailViewModel
import com.blankj.utilcode.util.*
import kotlinx.android.synthetic.main.fragment_news_detail.*
import java.util.concurrent.Executors

class NewsDetailFragment : BaseFragment() {

    private val args: NewsDetailFragmentArgs by navArgs()

    @Suppress("UNCHECKED_CAST")
    private val model: NewsDetailViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return NewsDetailViewModel(
                    NewsRepository(
                        NetClient.create(NewsService::class.java),
                        AppDatabase.getInstance(context),
                        Executors.newFixedThreadPool(1)
                    )
                ) as T
            }

        }
    }

    override fun layoutRes(): Int {
        return R.layout.fragment_news_detail
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bind = FragmentNewsDetailBinding.bind(view)
        bind.newsDetail = model
        model.getData(args.newsId)
        bind.lifecycleOwner = this
        swipe_layout.setOnRefreshListener {
            model.getData(args.newsId)
        }
        toolbar.setNavigationOnClickListener {
            nav().popBackStack()
        }
        ctlTitle.setExpandedTitleColor(Color.WHITE)
        ctlTitle.setCollapsedTitleTextColor(Color.WHITE)
        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.action_search) {
                ll_container.setBackgroundColor(Color.WHITE)
                val io = Executors.newCachedThreadPool()
                io.execute {
                    val path =
                        PathUtils.getInternalAppCachePath() + "/${args.newsId}.jpg"
                    if (!FileUtils.isFileExists(path)) {
                        val view2Bitmap = view2Bitmap(ll_container)
                        val bytes = ImageUtils.compressByQuality(view2Bitmap, 30, true)
                        val bytes2Bitmap = ImageUtils.bytes2Bitmap(bytes)
                        ImageUtils.save(bytes2Bitmap, path, Bitmap.CompressFormat.JPEG, true)
                    }
//                 val shareTextIntent = IntentUtils.getShareTextIntent(textView.text.toString())

                    val shareImageIntent =
                        IntentUtils.getShareImageIntent("", path)
                    startActivity(shareImageIntent)
                }

            }
            true
        }
    }

    fun view2Bitmap(view: View?): Bitmap? {
        LogUtils.e(view?.measuredWidth, view?.measuredHeight)
        if (view == null) return null
        val drawingCacheEnabled = view.isDrawingCacheEnabled
        val willNotCacheDrawing = view.willNotCacheDrawing()
        view.isDrawingCacheEnabled = true
        view.setWillNotCacheDrawing(false)
        var drawingCache = view.drawingCache
        val bitmap: Bitmap
        if (null == drawingCache) {
//            view.measure(
//                View.MeasureSpec.makeMeasureSpec(
//                    0,
//                    View.MeasureSpec.UNSPECIFIED
//                ),
//                View.MeasureSpec.makeMeasureSpec(
//                    0,
//                    View.MeasureSpec.UNSPECIFIED
//                )
//            )
//            view.layout(0, 0, view.measuredWidth, view.measuredHeight)
            LogUtils.e(view.measuredWidth, view.measuredHeight)
            view.buildDrawingCache()
            drawingCache = view.drawingCache
            if (drawingCache != null) {
                bitmap = Bitmap.createBitmap(drawingCache)
            } else {
                bitmap = Bitmap.createBitmap(
                    view.measuredWidth,
                    view.measuredHeight,
                    Bitmap.Config.ARGB_8888
                )
                val canvas = Canvas(bitmap)
                view.draw(canvas)
            }
        } else {
            bitmap = Bitmap.createScaledBitmap(
                drawingCache,
                view.measuredWidth,
                view.measuredHeight,
                false
            )
        }
        view.destroyDrawingCache()
        view.setWillNotCacheDrawing(willNotCacheDrawing)
        view.isDrawingCacheEnabled = drawingCacheEnabled
        return bitmap
    }
}