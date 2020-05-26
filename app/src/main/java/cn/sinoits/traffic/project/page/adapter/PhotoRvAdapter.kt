package cn.sinoits.traffic.project.page.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cn.sinoits.traffic.project.data.entity.Photo
import cn.sinoits.traffic.project.databinding.ItemRvPhotoBinding

class PhotoRvAdapter : PagedListAdapter<Photo, PhotoRvAdapter.Companion.Holder>(
    diff
) {

    companion object {
        class Holder(val binding: ItemRvPhotoBinding) : RecyclerView.ViewHolder(binding.root)

        val diff = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            ItemRvPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = getItem(position)
        holder.binding.photo = data
        holder.binding.executePendingBindings()
    }

}