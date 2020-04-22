package cn.linhome.kotlinmvpsamples.mvp.model

import cn.linhome.kotlinmvpsamples.base.BaseModel
import cn.linhome.kotlinmvpsamples.http.RetrofitHelper
import cn.linhome.kotlinmvpsamples.model.bean.HotSearchBean
import cn.linhome.kotlinmvpsamples.model.bean.HttpResult
import cn.linhome.kotlinmvpsamples.mvp.contract.SearchContract
import io.reactivex.Observable

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/22
 */
class SearchModel : BaseModel(), SearchContract.Model{
    override fun getHotSearchData(): Observable<HttpResult<MutableList<HotSearchBean>>> {
        return RetrofitHelper.service.getHotSearchData()
    }
}