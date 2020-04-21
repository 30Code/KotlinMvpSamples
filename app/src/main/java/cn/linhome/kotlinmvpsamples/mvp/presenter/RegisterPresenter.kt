package cn.linhome.kotlinmvpsamples.mvp.presenter

import cn.linhome.kotlinmvpsamples.base.BasePresenter
import cn.linhome.kotlinmvpsamples.ext.ss
import cn.linhome.kotlinmvpsamples.mvp.contract.RegisterContract
import cn.linhome.kotlinmvpsamples.mvp.model.RegisterModel

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/21
 */
class RegisterPresenter : BasePresenter<RegisterContract.Model, RegisterContract.View>(), RegisterContract.Presenter{

    override fun createModel(): RegisterContract.Model? = RegisterModel()

    override fun register(userName: String, pwd: String, pwd2: String) {
        mModel?.register(userName, pwd, pwd2)?.ss(mModel, mView) {
            mView?.apply {
                if (it.errorCode != 0) {
                    registerFail()
                } else {
                    registerSuccess(it.data)
                }
            }
        }
    }
}