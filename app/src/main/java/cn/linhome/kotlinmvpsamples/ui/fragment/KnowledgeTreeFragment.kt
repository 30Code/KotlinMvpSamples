package cn.linhome.kotlinmvpsamples.ui.fragment

import android.view.View
import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.base.BaseFragment

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/15
 */
class KnowledgeTreeFragment : BaseFragment() {

    override fun initView(view: View) {

    }

    override fun lazyLoad() {
    }

    override fun onCreateContentView(): Int = R.layout.frag_knowledge_tree
}