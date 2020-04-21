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
 *  date : 2020/4/21
 */
interface RegisterContract {

    interface View : IView {
        fun registerSuccess(data : LoginData)
        fun registerFail()
    }

    interface Presenter : IPresenter<View> {
        fun register(userName : String, pwd : String, pwd2 : String)
    }

    interface Model : IModel {
        fun register(userName: String, pwd: String, pwd2: String) : Observable<HttpResult<LoginData>>
    }

}