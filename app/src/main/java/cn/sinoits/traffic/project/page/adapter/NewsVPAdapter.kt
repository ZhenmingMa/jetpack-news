package cn.sinoits.traffic.project.page.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import cn.sinoits.traffic.project.data.entity.NewsType
import cn.sinoits.traffic.project.page.NewsListFragment

class NewsVPAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    var list: List<NewsType> = listOf()
    override fun getItem(position: Int): Fragment {
        val fragment = NewsListFragment()
        fragment.arguments = Bundle().apply {
            putInt("id", list[position].typeId)
            putString("name", list[position].typeName)
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return list[position].typeName
    }

    override fun getCount(): Int {
        return list.size
    }

}