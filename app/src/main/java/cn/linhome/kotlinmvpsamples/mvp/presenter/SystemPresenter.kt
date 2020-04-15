package cn.linhome.kotlinmvpsamples.mvp.presenter

import cn.linhome.kotlinmvpsamples.base.BasePresenter
import cn.linhome.kotlinmvpsamples.mvp.contract.SystemContract

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/15
 */
class SystemPresenter : BasePresenter<SystemContract.Model, SystemContract.View>(), SystemContract.Presenter{
}