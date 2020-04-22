package cn.linhome.kotlinmvpsamples.mvp.model

import cn.linhome.kotlinmvpsamples.base.BaseModel
import cn.linhome.kotlinmvpsamples.http.RetrofitHelper
import cn.linhome.kotlinmvpsamples.model.bean.HttpResult
import cn.linhome.kotlinmvpsamples.model.bean.UserInfoBody
import cn.linhome.kotlinmvpsamples.mvp.contract.MainContract
import io.reactivex.Observable

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/6
 */
class MainModel : BaseModel(), MainContract.Model{
    override fun logout(): Observable<HttpResult<Any>> {
        return RetrofitHelper.service.logout()
    }

    override fun getUserInfo(): Observable<HttpResult<UserInfoBody>> {
        return RetrofitHelper.service.getUserInfo()
    }
}