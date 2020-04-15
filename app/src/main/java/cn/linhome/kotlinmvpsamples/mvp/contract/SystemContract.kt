package cn.linhome.kotlinmvpsamples.mvp.contract

import cn.linhome.kotlinmvpsamples.base.IModel
import cn.linhome.kotlinmvpsamples.base.IPresenter
import cn.linhome.kotlinmvpsamples.base.IView

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/15
 */
interface SystemContract {

    interface View : IView {
        fun scrollToTop()
    }

    interface Presenter : IPresenter<View> {

    }

    interface Model : IModel {

    }
}