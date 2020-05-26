package com.ma.jetpack.news.page.adapter

import android.text.Spanned
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ma.jetpack.news.GlideApp
import com.ma.jetpack.news.R
import com.ma.jetpack.news.data.api.NetworkState
import com.ma.jetpack.news.data.entity.ImagesItem
import com.ma.jetpack.news.page.view.SampleCoverVideo
import com.ma.jetpack.news.utils.HtmlHttpImageGetter
import com.blankj.utilcode.util.ToastUtils
import org.sufficientlysecure.htmltextview.HtmlFormatter
import org.sufficientlysecure.htmltextview.HtmlFormatterBuilder


@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        view.visibility = View.VISIBLE
        GlideApp
            .with(view.context)
            .load(imageUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_image_default_24dp)
            .into(view)
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("videoFromUrl", "coverUrl")
fun bindVideoFromUrl(view: SampleCoverVideo, videoUrl: Array<String>?, coverUrl: String?) {
    if (!videoUrl.isNullOrEmpty()) {
        view.visibility = View.VISIBLE
        view.setUp(videoUrl.get(0), true, null, null, "")
        view.getTitleTextView().setVisibility(View.GONE)
        view.getBackButton().setVisibility(View.GONE)
        view.loadCoverImage(coverUrl, R.drawable.ic_image_default_24dp)
        view.getFullscreenButton()
            .setOnClickListener(View.OnClickListener {
                view.startWindowFullscreen(
                    view.context,
                    true,
                    true
                )
            })
        view.isAutoFullWithSize = false
        view.isNeedShowWifiTip = false
        view.isReleaseWhenLossAudio = false
        view.isShowFullAnimation = true
        view.setIsTouchWiget(false)


    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("htmlText", "htmlImage")
fun bindHtmlTextView(textView: TextView, htmlText: String?, htmlImage: List<ImagesItem?>?) {

    var str = htmlText
    htmlImage?.let {
        for (img in htmlImage) {
            str = str?.replace(img?.position!!, "<img src=${img.imgSrc!!}>", false)
        }
    }
    str?.let {
        val formattedHtml: Spanned = HtmlFormatter.formatHtml(
            HtmlFormatterBuilder().setHtml(str)
                .setImageGetter(
                    HtmlHttpImageGetter(
                        textView,
                        null,
                        R.drawable.ic_image_default_24dp,
                        true
                    )
                )
        )
        textView.setText(formattedHtml)
    }

}

@BindingAdapter("isRefresh")
fun bindSwipeRefreshLayout(view: SwipeRefreshLayout, networkState: NetworkState?) {
    view.isRefreshing = when (networkState) {
        NetworkState.LOADING -> true
        else -> false
    }
    networkState?.msg?.let {
        ToastUtils.showShort(it)
    }
}
