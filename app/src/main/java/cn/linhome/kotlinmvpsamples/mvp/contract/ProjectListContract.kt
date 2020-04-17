package cn.linhome.kotlinmvpsamples.mvp.contract

import cn.linhome.kotlinmvpsamples.model.bean.ArticleResponseBody
import cn.linhome.kotlinmvpsamples.model.bean.HttpResult
import io.reactivex.Observable

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/17
 */
interface ProjectListContract {

    interface View : CommonContract.View {

        fun scrollToTop()

        fun setProjectList(articles: ArticleResponseBody)

    }

    interface Presenter : CommonContract.Presenter<View> {

        fun requestProjectList(page: Int, cid: Int)

    }

    interface Model : CommonContract.Model {

        fun requestProjectList(page: Int, cid: Int): Observable<HttpResult<ArticleResponseBody>>

    }

}