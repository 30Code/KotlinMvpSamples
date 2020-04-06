package cn.linhome.kotlinmvpsamples.base

import android.view.View
import cn.linhome.lib.utils.context.FToast

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/4
 */
abstract class BaseMvpFragment<in V : IView, P : IPresenter<V>> : BaseFragment(), IView{

    /**
     * Presenter
     */
    protected var mPresenter: P? = null

    protected abstract fun createPresenter(): P

    override fun initView(view: View) {
        mPresenter = createPresenter()
        mPresenter?.attachView(this as V)
    }

    override fun lazyLoad() {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showDefaultMsg(msg: String) {
        FToast.show(msg)
    }

    override fun showMsg(msg: String) {
        FToast.show(msg)
    }

    override fun showError(errorMsg: String) {
        FToast.show(errorMsg)
    }

}