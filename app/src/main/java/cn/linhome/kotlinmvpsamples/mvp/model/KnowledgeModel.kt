package cn.linhome.kotlinmvpsamples.mvp.model

import cn.linhome.kotlinmvpsamples.http.RetrofitHelper
import cn.linhome.kotlinmvpsamples.model.bean.ArticleResponseBody
import cn.linhome.kotlinmvpsamples.model.bean.HttpResult
import cn.linhome.kotlinmvpsamples.mvp.contract.KnowledgeContract
import io.reactivex.Observable

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/15
 */
class KnowledgeModel : CommonModel(), KnowledgeContract.Model {
    override fun requestKnowledgeList(
        page: Int,
        cid: Int
    ): Observable<HttpResult<ArticleResponseBody>> {
        return RetrofitHelper.service.getKnowledgeList(page, cid)
    }
}