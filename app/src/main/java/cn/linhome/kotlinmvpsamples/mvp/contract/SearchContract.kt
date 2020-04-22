package cn.linhome.kotlinmvpsamples.mvp.contract

import cn.linhome.kotlinmvpsamples.base.IModel
import cn.linhome.kotlinmvpsamples.base.IPresenter
import cn.linhome.kotlinmvpsamples.base.IView
import cn.linhome.kotlinmvpsamples.model.bean.HotSearchBean
import cn.linhome.kotlinmvpsamples.model.bean.HttpResult
import cn.linhome.kotlinmvpsamples.model.bean.SearchHistoryBean
import io.reactivex.Observable

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/22
 */
interface SearchContract {

    interface View : IView {
        fun showHistoryData(listHistoryBean : MutableList<SearchHistoryBean>)
        fun showHotSearchData(listHotBean : MutableList<HotSearchBean>)
    }

    interface Presenter : IPresenter<View> {
        fun getHotSearchData()
    }

    interface Model : IModel {
        fun getHotSearchData() : Observable<HttpResult<MutableList<HotSearchBean>>>
    }

}