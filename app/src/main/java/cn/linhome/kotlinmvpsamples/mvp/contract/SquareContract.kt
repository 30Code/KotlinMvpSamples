package cn.linhome.kotlinmvpsamples.mvp.contract

import cn.linhome.kotlinmvpsamples.model.bean.ArticleResponseBody
import cn.linhome.kotlinmvpsamples.model.bean.HttpResult
import io.reactivex.Observable

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/13
 */
interface SquareContract {

    interface View : CommonContract.View {
        fun scrollToTop()
        fun showSquareList(body: ArticleResponseBody)
    }

    interface Presenter : CommonContract.Presenter<View> {
        fun getSquareList(page : Int)
    }

    interface Model : CommonContract.Model {
        fun getSquareList(page : Int) : Observable<HttpResult<ArticleResponseBody>>
    }

}