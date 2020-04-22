package cn.linhome.kotlinmvpsamples.mvp.presenter

import cn.linhome.kotlinmvpsamples.base.BasePresenter
import cn.linhome.kotlinmvpsamples.ext.ss
import cn.linhome.kotlinmvpsamples.ext.sss
import cn.linhome.kotlinmvpsamples.mvp.contract.MainContract
import cn.linhome.kotlinmvpsamples.mvp.model.MainModel

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/6
 */
class MainPresenter : BasePresenter<MainContract.Model, MainContract.View>(), MainContract.Presenter{

    override fun createModel(): MainContract.Model? = MainModel();

    override fun logout() {
        mModel?.logout()?.ss(mModel, mView) {
            mView?.showLogoutSuccess(success = true)
        }
    }

    override fun getUserInfo() {
        mModel?.getUserInfo()?.sss(mView, false, {
            mView?.showUserInfo(it.data)
        }, {})
    }

}