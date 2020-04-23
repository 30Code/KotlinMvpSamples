package cn.linhome.kotlinmvpsamples.mvp.presenter

import cn.linhome.kotlinmvpsamples.ext.ss
import cn.linhome.kotlinmvpsamples.mvp.contract.SearchContentContract
import cn.linhome.kotlinmvpsamples.mvp.model.SearchContentModel

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/23
 */
class SearchContentPresenter : CommonPresenter<SearchContentContract.Model, SearchContentContract.View>(), SearchContentContract.Presenter{

    override fun createModel(): SearchContentContract.Model? = SearchContentModel()

    override fun queryBySearchKey(page: Int, key: String) {
        mModel?.queryBySearchKey(page, key)?.ss(mModel, mView) {
            mView?.showArticles(it.data)
        }
    }
}