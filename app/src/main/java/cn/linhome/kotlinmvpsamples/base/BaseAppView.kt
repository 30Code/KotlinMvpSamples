package cn.linhome.kotlinmvpsamples.base

import android.content.Context
import android.view.View
import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.app.App
import cn.linhome.kotlinmvpsamples.view.pulltorefresh.PullToRefreshViewWrapper
import cn.linhome.lib.pulltorefresh.FPullToRefreshView
import cn.linhome.lib.statelayout.FStateLayout
import cn.linhome.library.view.SDAppView

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/16
 */
abstract class BaseAppView : SDAppView{

    private var mPullToRefreshViewWrapper : PullToRefreshViewWrapper = PullToRefreshViewWrapper()
    private var mStateLayout : FStateLayout = FStateLayout(App.mContext)

    constructor(context : Context) : super(context)

    override fun onBaseInit() {
        super.onBaseInit()
        initView()
        start()
    }

    /**
     * 初始化 View
     */
    abstract fun initView()

    /**
     * 开始请求
     */
    abstract fun start()

    /**
     * 无网络状态 -> 有网络状态的自动重连操作，子类可重写该方法
     */
    open fun doReConnected() {
        start()
    }

    /**
     * 返回状态布局
     *
     * @return
     */
    fun getStateLayout(): FStateLayout {
        val stateLayout = findViewById<View>(R.id.view_state_layout)
        if (stateLayout is FStateLayout) {
            setStateLayout(stateLayout)
        }
        return mStateLayout
    }

    /**
     * 设置状态布局
     *
     * @param stateLayout
     */
    fun setStateLayout(stateLayout: FStateLayout) {
        if (mStateLayout !== stateLayout) {
            mStateLayout = stateLayout
            stateLayout.emptyView.setContentView(R.layout.view_state_empty_content)
            stateLayout.errorView.setContentView(R.layout.view_state_error_net)
        }
    }

    /**
     * 返回下拉刷新包裹对象
     *
     * @return
     */
    fun getPullToRefreshViewWrapper(): PullToRefreshViewWrapper? {
        mPullToRefreshViewWrapper = PullToRefreshViewWrapper()
        val pullToRefreshView = findViewById<View>(R.id.view_pull_to_refresh)
        if (pullToRefreshView is FPullToRefreshView) {
            mPullToRefreshViewWrapper.setPullToRefreshView(pullToRefreshView as FPullToRefreshView)
        }
        return mPullToRefreshViewWrapper
    }
}