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
        TODO("Not yet implemented")
        mPresenter = createPresenter()
        mPresenter?.attachView(this as V)
    }

    override fun lazyLoad() {
        TODO("Not yet implemented")
    }

    override fun showLoading() {
        TODO("Not yet implemented")
    }

    override fun hideLoading() {
        TODO("Not yet implemented")
    }

    override fun showDefaultMsg(msg: String) {
        TODO("Not yet implemented")
        FToast.show(msg)
    }

    override fun showMsg(msg: String) {
        TODO("Not yet implemented")
        FToast.show(msg)
    }

    override fun showError(errorMsg: String) {
        TODO("Not yet implemented")
        FToast.show(errorMsg)
    }

}