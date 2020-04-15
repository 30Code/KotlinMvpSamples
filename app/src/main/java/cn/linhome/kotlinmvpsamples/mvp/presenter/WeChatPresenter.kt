package cn.linhome.kotlinmvpsamples.mvp.presenter

import cn.linhome.kotlinmvpsamples.base.BasePresenter
import cn.linhome.kotlinmvpsamples.ext.ss
import cn.linhome.kotlinmvpsamples.mvp.contract.WeChatContract
import cn.linhome.kotlinmvpsamples.mvp.model.WeChatModel

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/15
 */
class WeChatPresenter : BasePresenter<WeChatContract.Model, WeChatContract.View>(), WeChatContract.Presenter{

    override fun createModel(): WeChatContract.Model? = WeChatModel()

    override fun getWXChapters() {
        mModel?.getWXChapters()?.ss(mModel, mView) {
            mView?.showWXChapters(it.data)
        }
    }
}