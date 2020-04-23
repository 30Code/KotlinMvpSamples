package cn.linhome.kotlinmvpsamples.mvp.contract

import cn.linhome.kotlinmvpsamples.model.bean.ArticleResponseBody
import cn.linhome.kotlinmvpsamples.model.bean.HttpResult
import io.reactivex.Observable

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/23
 */
interface SearchContentContract {

    interface View : CommonContract.View {
        fun showArticles(articles : ArticleResponseBody)
        fun scrollToTop()
    }

    interface Presenter : CommonContract.Presenter<View> {
        fun queryBySearchKey(page : Int, key : String)
    }

    interface Model : CommonContract.Model {
        fun queryBySearchKey(page: Int, key: String) : Observable<HttpResult<ArticleResponseBody>>
    }

}