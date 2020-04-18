package cn.linhome.kotlinmvpsamples.mvp.presenter

import cn.linhome.kotlinmvpsamples.base.BasePresenter
import cn.linhome.kotlinmvpsamples.ext.ss
import cn.linhome.kotlinmvpsamples.mvp.contract.LoginContract
import cn.linhome.kotlinmvpsamples.mvp.model.LoginModel

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/18
 */
class LoginPresenter : BasePresenter<LoginContract.Model, LoginContract.View>(), LoginContract.Presenter{

    override fun createModel(): LoginContract.Model? = LoginModel()

    override fun login(userName: String, password: String) {
        mModel?.login(userName, password)?.ss(mModel, mView) {
            mView?.loginSuccess(it.data)
        }
    }
}