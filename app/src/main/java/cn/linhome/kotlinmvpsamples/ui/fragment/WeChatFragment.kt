package cn.linhome.kotlinmvpsamples.ui.fragment

import android.view.View
import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.adapter.WeChatPagerAdapter
import cn.linhome.kotlinmvpsamples.base.BaseMvpFragment
import cn.linhome.kotlinmvpsamples.model.bean.WXChapterBean
import cn.linhome.kotlinmvpsamples.mvp.contract.WeChatContract
import cn.linhome.kotlinmvpsamples.mvp.presenter.WeChatPresenter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.frag_wechat.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/6
 */
class WeChatFragment : BaseMvpFragment<WeChatContract.View, WeChatContract.Presenter>(), WeChatContract.View{

    companion object {
        fun getInstance(): WeChatFragment = WeChatFragment()
    }

    /**
     * datas
     */
    private val mListDatas = mutableListOf<WXChapterBean>()

    /**
     * ViewPagerAdapter
     */
    private val mViewPagerAdapter: WeChatPagerAdapter by lazy {
        WeChatPagerAdapter(mListDatas, childFragmentManager)
    }

    override fun createPresenter(): WeChatContract.Presenter = WeChatPresenter()

    override fun onCreateContentView(): Int = R.layout.frag_wechat

    override fun initView(view: View) {
        super.initView(view)
        viewPager.run {
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        }
        tabLayout.run {
            setupWithViewPager(viewPager)
            addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
            addOnTabSelectedListener(mOnTabSelectedList)
        }
    }

    override fun lazyLoad() {
        mPresenter?.getWXChapters()
    }

    override fun scrollToTop() {
        if (mViewPagerAdapter.count == 0) {
            return
        }
        val fragment : KnowledgeFragment = mViewPagerAdapter.getItem(viewPager.currentItem) as KnowledgeFragment
        fragment.scrollToTop()
    }

    override fun showWXChapters(chapters: MutableList<WXChapterBean>) {
        chapters.let {
            mListDatas.addAll(it)
            doAsync {
                Thread.sleep(10)
                uiThread {
                    viewPager.run {
                        adapter = mViewPagerAdapter
                        offscreenPageLimit = mListDatas.size
                    }
                }
            }
        }
    }

    protected val mOnTabSelectedList = object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {

        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
        }

        override fun onTabSelected(tab: TabLayout.Tab?) {
            // 默认切换的时候，会有一个过渡动画，设为false后，取消动画，直接显示
            tab?.let {
                viewPager.setCurrentItem(it.position, false)
            }
        }

    }
}