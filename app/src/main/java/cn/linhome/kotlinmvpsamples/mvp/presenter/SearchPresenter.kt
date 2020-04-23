package cn.linhome.kotlinmvpsamples.mvp.presenter

import cn.linhome.kotlinmvpsamples.base.BasePresenter
import cn.linhome.kotlinmvpsamples.dao.SearchHistoryDao
import cn.linhome.kotlinmvpsamples.ext.ss
import cn.linhome.kotlinmvpsamples.model.bean.SearchHistoryBean
import cn.linhome.kotlinmvpsamples.mvp.contract.SearchContract
import cn.linhome.kotlinmvpsamples.mvp.model.SearchModel
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

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

    override fun queryHistory() {
        val historyBeans = SearchHistoryDao.get().query()
        historyBeans.reverse()
        mView?.showHistoryData(historyBeans)
    }

    override fun saveSearchKey(key: String) {
        SearchHistoryDao.get().put(SearchHistoryBean(key))
    }

    override fun deleteId(key: String) {
        SearchHistoryDao.get().remove(SearchHistoryBean(key))
    }

    override fun clearAllHistory() {
        doAsync {
            SearchHistoryDao.get().clear()
            val historyBeans = SearchHistoryDao.get().query()
            uiThread {
                mView?.showHistoryData(historyBeans)
            }
        }
    }
}