package cn.linhome.kotlinmvpsamples.mvp.presenter

import cn.linhome.kotlinmvpsamples.ext.ss
import cn.linhome.kotlinmvpsamples.mvp.contract.SquareContract
import cn.linhome.kotlinmvpsamples.mvp.model.SquareModel

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/13
 */
class SquarePresenter : CommonPresenter<SquareModel, SquareContract.View>(), SquareContract.Presenter{

    override fun createModel(): SquareModel? = SquareModel()

    override fun getSquareList(page: Int) {
        mModel?.getSquareList(page)?.ss(mModel, mView, page == 0) {
            mView?.showSquareList(it.data)
        }
    }
}