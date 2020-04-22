package cn.linhome.kotlinmvpsamples.mvp.presenter

import cn.linhome.kotlinmvpsamples.base.BasePresenter
import cn.linhome.kotlinmvpsamples.ext.ss
import cn.linhome.kotlinmvpsamples.mvp.contract.SearchContract
import cn.linhome.kotlinmvpsamples.mvp.model.SearchModel

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/22
 */
class SearchPresenter : BasePresenter<SearchContract.Model, SearchContract.View>(), SearchContract.Presenter{

    override fun createModel(): SearchContract.Model? = SearchModel()

    override fun getHotSearchData() {
        mModel?.getHotSearchData()?.ss(mModel, mView) {
            mView?.showHotSearchData(it.data)
        }
    }
}