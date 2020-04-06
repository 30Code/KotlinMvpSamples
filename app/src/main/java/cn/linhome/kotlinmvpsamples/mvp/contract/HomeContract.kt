package cn.linhome.kotlinmvpsamples.mvp.contract

import cn.linhome.kotlinmvpsamples.model.bean.Article
import cn.linhome.kotlinmvpsamples.model.bean.ArticleResponseBody
import cn.linhome.kotlinmvpsamples.model.bean.Banner
import cn.linhome.kotlinmvpsamples.model.bean.HttpResult
import io.reactivex.Observable

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/6
 */
interface HomeContract {

    interface View :  CommonContract.View{
        fun scrollToTop()

        fun setBanner(banners: List<Banner>)

        fun setArticles(articles : ArticleResponseBody)
    }

    interface Presenter : CommonContract.Presenter<View> {
        fun requestBanner()

        fun requestHomeData()

        fun requestArticles(num : Int)
    }

    interface Model : CommonContract.Model {
        fun requestBanner() : Observable<HttpResult<List<Banner>>>

        fun requestTopArticles() : Observable<HttpResult<MutableList<Article>>>

        fun requestArticles(num : Int) : Observable<HttpResult<ArticleResponseBody>>
    }
}