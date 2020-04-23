package cn.linhome.kotlinmvpsamples.base

import android.app.Activity
import android.content.Context
import android.util.AttributeSet

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/23
 */
abstract class BaseAppMvpView<in V : IView, P : IPresenter<V>> : BaseAppView, IView {

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {}

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context?) : super(context) {}

    protected var mPresenter : P? = null

    protected abstract fun createdPresenter() : P

    override fun onBaseInit() {
        super.onBaseInit()
        mPresenter = createdPresenter()
        mPresenter?.attachView(this as V)
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