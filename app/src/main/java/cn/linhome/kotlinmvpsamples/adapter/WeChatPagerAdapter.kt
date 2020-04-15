package cn.linhome.kotlinmvpsamples.adapter

import android.text.Html
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import cn.linhome.kotlinmvpsamples.model.bean.WXChapterBean
import cn.linhome.kotlinmvpsamples.ui.fragment.KnowledgeFragment

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/15
 */
class WeChatPagerAdapter(val list : MutableList<WXChapterBean>, fm : FragmentManager?)
    : FragmentStatePagerAdapter(fm!!){

    private val fragments = mutableListOf<Fragment>()

    init {
        fragments.clear()
        list.forEach {
            fragments.add(KnowledgeFragment.getInstance(it.id))
        }
    }

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = list.size

    override fun getPageTitle(position: Int): CharSequence? = Html.fromHtml(list[position].name)

    override fun getItemPosition(`object`: Any): Int = PagerAdapter.POSITION_NONE
}