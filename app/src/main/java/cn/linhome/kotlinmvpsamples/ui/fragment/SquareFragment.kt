package cn.linhome.kotlinmvpsamples.ui.fragment

import android.view.View
import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.base.BaseFragment

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/6
 */
class SquareFragment : BaseFragment(){

    companion object {
        fun getInstance(): SquareFragment = SquareFragment()
    }

    override fun onCreateContentView(): Int = R.layout.frag_square

    override fun initView(view: View) {

    }

    override fun lazyLoad() {

    }
}