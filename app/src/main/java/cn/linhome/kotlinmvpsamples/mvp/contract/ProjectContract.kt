package cn.linhome.kotlinmvpsamples.mvp.contract

import cn.linhome.kotlinmvpsamples.base.IModel
import cn.linhome.kotlinmvpsamples.base.IPresenter
import cn.linhome.kotlinmvpsamples.base.IView
import cn.linhome.kotlinmvpsamples.model.bean.HttpResult
import cn.linhome.kotlinmvpsamples.model.bean.ProjectTreeBean
import io.reactivex.Observable

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/17
 */
interface ProjectContract {

    interface View : IView {
        fun scrollToTop()
        fun setProjectTree(list : List<ProjectTreeBean>)
    }

    interface Presenter : IPresenter<View> {
        fun requestProjectTree()
    }

    interface Model : IModel {
        fun requestProjectTree() : Observable<HttpResult<List<ProjectTreeBean>>>
    }

}