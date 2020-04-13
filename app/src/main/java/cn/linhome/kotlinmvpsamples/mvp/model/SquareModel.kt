package cn.linhome.kotlinmvpsamples.mvp.model

import cn.linhome.kotlinmvpsamples.http.RetrofitHelper
import cn.linhome.kotlinmvpsamples.model.bean.ArticleResponseBody
import cn.linhome.kotlinmvpsamples.model.bean.HttpResult
import cn.linhome.kotlinmvpsamples.mvp.contract.SquareContract
import io.reactivex.Observable

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/13
 */
class SquareModel : CommonModel(), SquareContract.Model{
    override fun getSquareList(page: Int): Observable<HttpResult<ArticleResponseBody>> {
        return RetrofitHelper.service.getSquareList(page)
    }
}