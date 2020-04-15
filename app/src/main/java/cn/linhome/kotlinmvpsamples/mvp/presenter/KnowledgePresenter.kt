package cn.linhome.kotlinmvpsamples.mvp.presenter

import cn.linhome.kotlinmvpsamples.ext.ss
import cn.linhome.kotlinmvpsamples.mvp.contract.KnowledgeContract
import cn.linhome.kotlinmvpsamples.mvp.model.KnowledgeModel

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/15
 */
class KnowledgePresenter : CommonPresenter<KnowledgeContract.Model, KnowledgeContract.View>(), KnowledgeContract.Presenter {

    override fun createModel(): KnowledgeContract.Model? = KnowledgeModel()

    override fun requestKnowledgeList(page: Int, cid: Int) {
        mModel?.requestKnowledgeList(page, cid)?.ss(mModel, mView) {
            mView?.setKnowledgeList(it.data)
        }
    }
}