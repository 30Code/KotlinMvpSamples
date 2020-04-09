package cn.linhome.kotlinmvpsamples.base

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.app.App
import cn.linhome.kotlinmvpsamples.utils.SettingUtil
import cn.linhome.kotlinmvpsamples.view.pulltorefresh.PullToRefreshViewWrapper
import cn.linhome.lib.pulltorefresh.FPullToRefreshView
import cn.linhome.lib.statelayout.FStateLayout
import cn.linhome.lib.title.FTitle
import cn.linhome.lib.title.FTitleItem
import cn.linhome.lib.utils.FViewUtil
import cn.linhome.lib.utils.context.FContext
import cn.linhome.lib.utils.context.FResUtil
import cn.linhome.lib.utils.context.FToast
import cn.linhome.library.activity.SDBaseActivity
import qiu.niorgai.StatusBarCompat

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/4
 */
abstract class BaseActivity : SDBaseActivity(), FTitle.Callback{

    /**
     * theme color
     */
    protected var mThemeColor: Int = SettingUtil.getColor()

    /**
     * 触摸返回键是否退出App
     */
    protected var mIsExitApp : Boolean = false
    protected var mExitTime : Long = 0

    private lateinit var mPullToRefreshViewWrapper : PullToRefreshViewWrapper
    private lateinit var mStateLayout : FStateLayout

    private lateinit var mTitleView : FTitle
//
//    private AppDialogProgress mProgressDialog;

    override fun onResume() {
        super.onResume()
        initColor()
    }

    open fun initColor(){
        mThemeColor = if (!SettingUtil.getIsNightMode()) {
            SettingUtil.getColor()
        } else {
            resources.getColor(R.color.colorPrimary)
        }
        StatusBarCompat.setStatusBarColor(this, mThemeColor)
        if (mTitleView != null){
            mTitleView.setBackgroundDrawable(ColorDrawable(mThemeColor))
        }
    }

    override fun init(savedInstanceState: Bundle?) {
        showTitle(false)
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

    override fun onCreateTitleViewResId(): Int {
        return R.layout.include_title_simple
    }

    override fun onInitTitleView(view: View) {
        super.onInitTitleView(view)
        mTitleView = view.findViewById(R.id.title)
        mTitleView.setCallback(this)
        mTitleView.itemLeft.setImageLeft(R.drawable.ic_arrow_left_gold)
        mTitleView.itemMiddle.tv_top.setTextColor(FResUtil.getResources().getColor(R.color.colorPrimary))
    }

    fun getTitleView(): FTitle {
        return mTitleView
    }

    protected open fun showTitle(show: Boolean) {
        if (show) {
            FViewUtil.setVisibility(getTitleView(), View.VISIBLE)
        } else {
            FViewUtil.setVisibility(getTitleView(), View.GONE)
        }
    }

    override fun onClickItemRightTitleBar(index: Int, item: FTitleItem?) {

    }

    override fun onClickItemMiddleTitleBar(index: Int, item: FTitleItem?) {

    }

    override fun onClickItemLeftTitleBar(index: Int, item: FTitleItem?) {
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

    fun exitApp(){
        if (System.currentTimeMillis() - mExitTime > 2000) {
            FToast.show(FContext.get().getString(R.string.press_again_exit))
        } else {
            App.exitApp(true)
        }
        mExitTime = System.currentTimeMillis()
    }

    override fun onBackPressed() {
        if (mIsExitApp) {
            exitApp()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        App.getRefWatcher(this)?.watch(this)
    }
}