package cn.linhome.kotlinmvpsamples.mvp.model

import cn.linhome.kotlinmvpsamples.base.BaseModel
import cn.linhome.kotlinmvpsamples.http.RetrofitHelper
import cn.linhome.kotlinmvpsamples.model.bean.HttpResult
import cn.linhome.kotlinmvpsamples.model.bean.LoginData
import cn.linhome.kotlinmvpsamples.mvp.contract.LoginContract
import io.reactivex.Observable

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/18
 */
class LoginModel : BaseModel(), LoginContract.Model {
    override fun login(userName: String, password: String): Observable<HttpResult<LoginData>> {
        return RetrofitHelper.service.login(userName, password)
    }
}