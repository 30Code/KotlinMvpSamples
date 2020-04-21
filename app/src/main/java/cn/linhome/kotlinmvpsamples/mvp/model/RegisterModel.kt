package cn.linhome.kotlinmvpsamples.mvp.model

import cn.linhome.kotlinmvpsamples.base.BaseModel
import cn.linhome.kotlinmvpsamples.http.RetrofitHelper
import cn.linhome.kotlinmvpsamples.model.bean.HttpResult
import cn.linhome.kotlinmvpsamples.model.bean.LoginData
import cn.linhome.kotlinmvpsamples.mvp.contract.RegisterContract
import io.reactivex.Observable

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/21
 */
class RegisterModel : BaseModel(), RegisterContract.Model{
    override fun register(
        userName: String,
        pwd: String,
        pwd2: String
    ): Observable<HttpResult<LoginData>> {
        return RetrofitHelper.service.register(userName, pwd, pwd2)
    }
}