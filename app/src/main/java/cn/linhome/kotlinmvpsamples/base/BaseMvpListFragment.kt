package cn.linhome.kotlinmvpsamples.base

import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cn.linhome.kotlinmvpsamples.R
import cn.linhome.library.view.SDRecyclerView.OnScrollCallBack
import kotlinx.android.synthetic.main.frag_square.*

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/12
 */
abstract class BaseMvpListFragment<in V : IView, P : IPresenter<V>> : BaseMvpFragment<V, P>() {

    /**
     * 每页数据的个数
     */
    protected var mPageSize = 20

    /**
     * 是否是下拉刷新
     */
    protected var mIsRefresh = true

    /**
     * RefreshListener
     */
    protected val mOnRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        mIsRefresh = true
        onRefreshList()
    }

    override fun initView(view: View) {
        super.initView(view)

        swipeRefreshLayout.run {
            setColorSchemeResources(
                R.color.Cyan,
                R.color.Teal,
                R.color.Green)
            setOnRefreshListener(mOnRefreshListener)
        }

        rv_list.run {
            itemAnimator = DefaultItemAnimator()
//            addItemDecoration()
            addOnScrollCallBack (object : OnScrollCallBack {
                override fun onLoadMore() {
                    mIsRefresh = false
                    swipeRefreshLayout.isRefreshing = false
                    onLoadMoreList()
                }

            })
        }
    }

    /**
     * 下拉刷新
     */
    abstract fun onRefreshList()

    /**
     * 上拉加载更多
     */
    abstract fun onLoadMoreList()

    override fun hideLoading() {
        super.hideLoading()
        swipeRefreshLayout?.isRefreshing = false
    }

}