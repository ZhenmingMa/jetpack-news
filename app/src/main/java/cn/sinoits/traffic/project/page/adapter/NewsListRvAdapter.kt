package cn.sinoits.traffic.project.page.adapter

import android.view.*
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cn.sinoits.traffic.project.GlideApp
import cn.sinoits.traffic.project.data.entity.NewsList
import cn.sinoits.traffic.project.databinding.ItemRvNewsBinding
import cn.sinoits.traffic.project.page.NewsFragmentDirections
import cn.sinoits.traffic.project.page.adapter.NewsListRvAdapter.Holder.Companion.TAG
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.listener.GSYMediaPlayerListener
import tv.danmaku.ijk.media.exo.demo.player.DemoPlayer

class NewsListRvAdapter() :
    PagedListAdapter<NewsList, NewsListRvAdapter.Holder>(diff) {

    class Holder(val binding: ItemRvNewsBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            val TAG = "RecyclerView2List"
        }
    }

    companion object {
        val diff = object : DiffUtil.ItemCallback<NewsList>() {
            override fun areItemsTheSame(oldItem: NewsList, newItem: NewsList): Boolean {
                LogUtils.e("areItemsTheSame", oldItem, newItem)
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: NewsList, newItem: NewsList): Boolean {
                LogUtils.e("areContentsTheSame", oldItem.newsId, newItem.newsId)
                return oldItem.newsId == newItem.newsId
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            ItemRvNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val newsList = getItem(position)
        holder.binding.newsList = newsList
        holder.binding.executePendingBindings()

        if (!newsList?.videoList.isNullOrEmpty()) {
            holder.binding.iv.visibility = View.GONE
            holder.binding.detailPlayer.setPlayTag(TAG)
            holder.binding.detailPlayer.setPlayPosition(position)
            holder.binding.setOnItemClick {
                holder.binding.detailPlayer.onVideoPause()
            }
        } else {
            holder.binding.setOnItemClick {
                it.findNavController().navigate(
                    NewsFragmentDirections.actionNewsFragmentToNewsDetailFragment(newsList?.newsId!!)
                )
            }
        }


    }
}