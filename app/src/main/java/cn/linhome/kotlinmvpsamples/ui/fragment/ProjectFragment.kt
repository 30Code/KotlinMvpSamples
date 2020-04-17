package cn.linhome.kotlinmvpsamples.ui.fragment

import android.view.View
import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.adapter.ProjectPagerAdapter
import cn.linhome.kotlinmvpsamples.base.BaseMvpFragment
import cn.linhome.kotlinmvpsamples.model.bean.ProjectTreeBean
import cn.linhome.kotlinmvpsamples.mvp.contract.ProjectContract
import cn.linhome.kotlinmvpsamples.mvp.presenter.ProjectPresenter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.frag_wechat.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/6
 */
class ProjectFragment : BaseMvpFragment<ProjectContract.View, ProjectContract.Presenter>(), ProjectContract.View {

    companion object {
        fun getInstance(): ProjectFragment = ProjectFragment()
    }

    private var mListProjectTree = mutableListOf<ProjectTreeBean>()

    private val mViewPagerAdapter: ProjectPagerAdapter by lazy {
        ProjectPagerAdapter(mListProjectTree, childFragmentManager)
    }

    override fun createPresenter(): ProjectContract.Presenter = ProjectPresenter()

    override fun onCreateContentView(): Int = R.layout.frag_project

    override fun initView(view: View) {
        super.initView(view)
        mLayoutStatusView = multiple_status_view
        viewPager.run {
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        }

        tabLayout.run {
            setupWithViewPager(viewPager)
            // TabLayoutHelper.setUpIndicatorWidth(tabLayout)
            addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
            addOnTabSelectedListener(mOnTabSelectedListener)
        }
    }

    override fun showLoading() {
        super.showLoading()
        mLayoutStatusView?.showLoading()
    }

    override fun lazyLoad() {
        mPresenter?.requestProjectTree()
    }

    override fun scrollToTop() {
        if (mViewPagerAdapter.count == 0) {
            return
        }
        val fragment : ProjectListFragment = mViewPagerAdapter.getItem(viewPager.currentItem) as ProjectListFragment
        fragment.scrollToTop()
    }

    override fun setProjectTree(list: List<ProjectTreeBean>) {
        list.let {
            mListProjectTree.addAll(it)
            doAsync {
                Thread.sleep(10)
                uiThread {
                    viewPager.run {
                        adapter = mViewPagerAdapter
                        offscreenPageLimit = mListProjectTree.size
                    }
                }
            }
        }
        if (list.isEmpty()) {
            mLayoutStatusView?.showEmpty()
        } else {
            mLayoutStatusView?.showContent()
        }
    }

    override fun showError(errorMsg: String) {
        super.showError(errorMsg)
        mLayoutStatusView?.showError()
    }

    private val mOnTabSelectedListener = object : TabLayout.OnTabSelectedListener {
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