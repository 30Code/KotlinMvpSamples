package cn.linhome.kotlinmvpsamples.base

import android.os.Bundle
import android.view.View
import cn.linhome.kotlinmvpsamples.app.App
import cn.linhome.library.fragment.SDBaseFragment

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/4
 */
abstract class BaseFragment : SDBaseFragment(){

    /**
     * 视图是否加载完毕
     */
    private var mIsViewPrepare = false
    /**
     * 数据是否加载过了
     */
    private var mHasLoadData = false

    /**
     * 初始化 View
     */
    abstract fun initView(view: View)

    /**
     * 懒加载
     */
    abstract fun lazyLoad()

    /**
     * 无网状态—>有网状态 的自动重连操作，子类可重写该方法
     */
    open fun doReConnected() {
        lazyLoad()
    }

    override fun onCreateContentView(): Int {
        TODO("Not yet implemented")
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyLoadDataIfPrepared()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mIsViewPrepare = true
        initView(view)
        lazyLoadDataIfPrepared()
        //多种状态切换的view 重试点击事件
//        mLayoutStatusView?.setOnClickListener(mRetryClickListener)
    }

    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && mIsViewPrepare && !mHasLoadData) {
            lazyLoad()
            mHasLoadData = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.let { App.getRefWatcher(it)?.watch(activity) }
    }

}