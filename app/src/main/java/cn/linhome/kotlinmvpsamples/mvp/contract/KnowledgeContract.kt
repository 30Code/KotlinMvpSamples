package cn.linhome.kotlinmvpsamples.mvp.contract

import cn.linhome.kotlinmvpsamples.model.bean.ArticleResponseBody
import cn.linhome.kotlinmvpsamples.model.bean.HttpResult
import io.reactivex.Observable

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/15
 */
interface KnowledgeContract {

    interface View : CommonContract.View {
        fun scrollToTop()
        fun setKnowledgeList(articles : ArticleResponseBody)
    }

    interface Presenter : CommonContract.Presenter<View> {
        fun requestKnowledgeList(page : Int, cid : Int)
    }

    interface Model : CommonContract.Model {
        fun requestKnowledgeList(page : Int, cid : Int) : Observable<HttpResult<ArticleResponseBody>>
    }

}