package cn.linhome.kotlinmvpsamples.base

import android.content.Context
import android.util.AttributeSet
import android.view.View
import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.view.pulltorefresh.PullToRefreshViewWrapper
import cn.linhome.lib.pulltorefresh.FPullToRefreshView
import cn.linhome.lib.statelayout.FStateLayout
import cn.linhome.library.view.SDAppView

/**
 * des :
 * Created by 30Code
 * date : 2020/4/22
 */
open class BaseAppView : SDAppView
{
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {}

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context?) : super(context) {}

    private var mStateLayout: FStateLayout? = null
    private var mPullToRefreshViewWrapper: PullToRefreshViewWrapper? = null

    /**
     * 返回状态布局
     *
     * @return
     */
    /**
     * 设置状态布局
     *
     * @param stateLayout
     */
    var stateLayout: FStateLayout?
        get() {
            if (mStateLayout == null) {
                var stateLayout =
                    findViewById<View>(R.id.view_state_layout)
                if (stateLayout is FStateLayout) {
                    stateLayout = stateLayout
                }
            }
            return mStateLayout
        }
        set(stateLayout) {
            if (mStateLayout !== stateLayout) {
                mStateLayout = stateLayout
                if (stateLayout != null) {
                    stateLayout.emptyView.setContentView(R.layout.view_state_empty_content)
                    stateLayout.errorView.setContentView(R.layout.view_state_error_net)
                }
            }
        }

    /**
     * 返回下拉刷新包裹对象
     *
     * @return
     */
    val pullToRefreshViewWrapper: PullToRefreshViewWrapper
        get() {
            if (mPullToRefreshViewWrapper == null) {
                mPullToRefreshViewWrapper = PullToRefreshViewWrapper()
                val pullToRefreshView =
                    findViewById<View>(R.id.view_pull_to_refresh)
                if (pullToRefreshView is FPullToRefreshView) {
                    mPullToRefreshViewWrapper!!.setPullToRefreshView(pullToRefreshView)
                }
            }
            return mPullToRefreshViewWrapper!!
        }
}