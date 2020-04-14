package cn.linhome.kotlinmvpsamples.base

import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.view.pulltorefresh.IPullToRefreshViewWrapper
import cn.linhome.lib.utils.context.FResUtil
import cn.linhome.lib.utils.extend.FDrawable
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
//    protected val mOnRefreshListener = SwipeRefreshLayout.OnRefreshListener {
//        mIsRefresh = true
//        onRefreshList()
//    }

    override fun initView(view: View) {
        super.initView(view)

//        swipeRefreshLayout.run {
//            setColorSchemeResources(
//                R.color.Cyan,
//                R.color.Teal,
//                R.color.Green)
//            setOnRefreshListener(mOnRefreshListener)
//        }

        getPullToRefreshViewWrapper()?.setOnRefreshCallbackWrapper(object :
            IPullToRefreshViewWrapper.OnRefreshCallbackWrapper {

            override fun onRefreshingFromHeader() {
                mIsRefresh = true
                onRefreshList()
            }

            override fun onRefreshingFromFooter() {
                mIsRefresh = false
                onLoadMoreList()
            }
        })

        rv_list.run {
            itemAnimator = DefaultItemAnimator()
            addDividerHorizontal(
                FDrawable().size(FResUtil.dp2px(1f))
                    .color(resources.getColor(R.color.res_bg_activity))
            )
            addOnScrollCallBack (object : OnScrollCallBack {
                override fun onLoadMore() {
//                    mIsRefresh = false
//                    swipeRefreshLayout.isRefreshing = false
//                    onLoadMoreList()
                    getPullToRefreshViewWrapper()?.startRefreshingFromFooter()
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
//        swipeRefreshLayout?.isRefreshing = false
    }

}