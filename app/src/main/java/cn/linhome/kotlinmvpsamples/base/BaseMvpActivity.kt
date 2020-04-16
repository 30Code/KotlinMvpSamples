package cn.linhome.kotlinmvpsamples.base

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/4
 */
abstract class BaseMvpActivity<in V : IView, P : IPresenter<V>> : BaseActivity(), IView{

    protected var mPresenter : P? = null

    protected abstract fun createdPresenter() : P

    override fun initView() {
        mPresenter = createdPresenter()
        mPresenter?.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
        this.mPresenter = null
    }

}