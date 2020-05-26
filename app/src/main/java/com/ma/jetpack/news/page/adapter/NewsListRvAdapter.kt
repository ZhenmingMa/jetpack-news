package com.ma.jetpack.news.page.adapter

import android.view.*
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ma.jetpack.news.data.entity.NewsList
import com.ma.jetpack.news.databinding.ItemRvNewsBinding
import com.ma.jetpack.news.page.NewsFragmentDirections
import com.ma.jetpack.news.page.adapter.NewsListRvAdapter.Holder.Companion.TAG
import com.blankj.utilcode.util.LogUtils

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