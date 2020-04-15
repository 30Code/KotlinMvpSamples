package cn.linhome.kotlinmvpsamples.mvp.contract

import cn.linhome.kotlinmvpsamples.base.IModel
import cn.linhome.kotlinmvpsamples.base.IPresenter
import cn.linhome.kotlinmvpsamples.base.IView
import cn.linhome.kotlinmvpsamples.model.bean.HttpResult
import cn.linhome.kotlinmvpsamples.model.bean.WXChapterBean
import io.reactivex.Observable

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/15
 */
interface WeChatContract {

    interface View : IView {

        fun scrollToTop()

        fun showWXChapters(chapters : MutableList<WXChapterBean>)

    }

    interface Presenter : IPresenter<View> {
        fun getWXChapters()
    }

    interface Model : IModel {
        fun getWXChapters() : Observable<HttpResult<MutableList<WXChapterBean>>>
    }

}