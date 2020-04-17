package cn.linhome.kotlinmvpsamples.adapter

import android.text.Html
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import cn.linhome.kotlinmvpsamples.model.bean.ProjectTreeBean
import cn.linhome.kotlinmvpsamples.ui.fragment.ProjectListFragment

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/17
 */
class ProjectPagerAdapter(private val list: MutableList<ProjectTreeBean>, fm: FragmentManager?) : FragmentStatePagerAdapter(fm!!) {

    private val fragments = mutableListOf<Fragment>()

    init {
        fragments.clear()
        list.forEach {
            fragments.add(ProjectListFragment.getInstance(it.id))
        }
    }

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = list.size

    override fun getPageTitle(position: Int): CharSequence? = Html.fromHtml(list[position].name)

    override fun getItemPosition(`object`: Any): Int = PagerAdapter.POSITION_NONE
}