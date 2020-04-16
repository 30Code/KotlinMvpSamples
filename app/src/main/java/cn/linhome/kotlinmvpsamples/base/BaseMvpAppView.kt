package cn.linhome.kotlinmvpsamples.base

import android.app.Activity
import android.content.Context

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/16
 */
abstract class BaseMvpAppView<in V : IView, P : IPresenter<V>> : BaseAppView, IView {

    protected var mPresenter : P? = null

    protected abstract fun createdPresenter() : P

    constructor(context :Context) : super(context)

    override fun initView() {
        mPresenter = createdPresenter()
        mPresenter?.attachView(this as V)
    }

    override fun start() {

    }

    override fun onActivityDestroyed(activity: Activity) {
        super.onActivityDestroyed(activity)
        mPresenter?.detachView()
        this.mPresenter = null
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showDefaultMsg(msg: String) {

    }

    override fun showMsg(msg: String) {

    }

    override fun showError(errorMsg: String) {

    }
}