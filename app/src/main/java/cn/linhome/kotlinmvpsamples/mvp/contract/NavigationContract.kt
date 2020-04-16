package cn.linhome.kotlinmvpsamples.mvp.contract

import cn.linhome.kotlinmvpsamples.base.IModel
import cn.linhome.kotlinmvpsamples.base.IPresenter
import cn.linhome.kotlinmvpsamples.base.IView
import cn.linhome.kotlinmvpsamples.model.bean.HttpResult
import cn.linhome.kotlinmvpsamples.model.bean.NavigationBean
import io.reactivex.Observable

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/16
 */
interface NavigationContract {

    interface View : IView {
        fun setNavigationData(list : List<NavigationBean>)
    }

    interface Presenter : IPresenter<View> {
        fun requestNavigationList()
    }

    interface Model : IModel {
        fun requestNavigationList() : Observable<HttpResult<List<NavigationBean>>>
    }
}