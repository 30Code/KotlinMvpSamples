package cn.linhome.kotlinmvpsamples.ui.fragment

import android.view.View
import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.base.BaseFragment

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/6
 */
class WeChatFragment : BaseFragment(){

    companion object {
        fun getInstance(): WeChatFragment = WeChatFragment()
    }

    override fun onCreateContentView(): Int = R.layout.frag_wechat

    override fun initView(view: View) {

    }

    override fun lazyLoad() {

    }
}