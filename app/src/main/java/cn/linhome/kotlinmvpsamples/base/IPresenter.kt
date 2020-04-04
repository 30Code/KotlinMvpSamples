package cn.linhome.kotlinmvpsamples.base

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/4
 */
interface IPresenter<in V : IView> {

    /**
     * 绑定 View
     */
    fun attachView(view: V)

    /**
     * 解绑 View
     */
    fun detachView()

}