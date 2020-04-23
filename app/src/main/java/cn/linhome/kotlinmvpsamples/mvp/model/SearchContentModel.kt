package cn.linhome.kotlinmvpsamples.mvp.model

import cn.linhome.kotlinmvpsamples.http.RetrofitHelper
import cn.linhome.kotlinmvpsamples.model.bean.ArticleResponseBody
import cn.linhome.kotlinmvpsamples.model.bean.HttpResult
import cn.linhome.kotlinmvpsamples.mvp.contract.SearchContentContract
import io.reactivex.Observable

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/23
 */
class SearchContentModel : CommonModel(), SearchContentContract.Model{
    override fun queryBySearchKey(
        page: Int,
        key: String
    ): Observable<HttpResult<ArticleResponseBody>> {
        return RetrofitHelper.service.queryBySearchKey(page, key)
    }
}