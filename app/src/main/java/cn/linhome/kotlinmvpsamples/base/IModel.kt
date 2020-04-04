package cn.linhome.kotlinmvpsamples.base

import io.reactivex.disposables.Disposable

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/4
 */
interface IModel {

    fun addDisposable(disposable: Disposable?)

    fun onDetach()

}