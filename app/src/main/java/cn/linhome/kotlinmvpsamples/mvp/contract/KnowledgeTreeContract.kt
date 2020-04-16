package cn.linhome.kotlinmvpsamples.mvp.contract

import cn.linhome.kotlinmvpsamples.base.IModel
import cn.linhome.kotlinmvpsamples.base.IPresenter
import cn.linhome.kotlinmvpsamples.base.IView
import cn.linhome.kotlinmvpsamples.model.bean.HttpResult
import cn.linhome.kotlinmvpsamples.model.bean.KnowledgeTreeBody
import io.reactivex.Observable

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/16
 */
interface KnowledgeTreeContract {

    interface View : IView {
        fun scrollToTop()
        fun setKnowledgeTree(list: List<KnowledgeTreeBody>)
    }

    interface Presenter : IPresenter<View> {
        fun requestKnowledgeTree()
    }

    interface Model : IModel {
        fun requestKnowledgeTree() : Observable<HttpResult<List<KnowledgeTreeBody>>>
    }

}