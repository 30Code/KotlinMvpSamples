package cn.linhome.kotlinmvpsamples.mvp.contract

import cn.linhome.kotlinmvpsamples.base.IModel
import cn.linhome.kotlinmvpsamples.base.IPresenter
import cn.linhome.kotlinmvpsamples.base.IView
import cn.linhome.kotlinmvpsamples.model.bean.HttpResult
import cn.linhome.kotlinmvpsamples.model.bean.LoginData
import io.reactivex.Observable

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/18
 */
interface LoginContract {

    interface View : IView {
        fun loginSuccess(data : LoginData)
        fun loginFail()
    }

    interface Presenter : IPresenter<View> {
        fun login(userName : String, password : String)
    }

    interface  Model : IModel {
        fun login(userName : String, password : String) : Observable<HttpResult<LoginData>>
    }

}