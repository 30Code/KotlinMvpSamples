package cn.linhome.kotlinmvpsamples.adapter

import android.app.Activity
import android.view.ViewGroup
import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.model.bean.Article
import cn.linhome.lib.adapter.FSimpleRecyclerAdapter
import cn.linhome.lib.adapter.viewholder.FRecyclerViewHolder

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/7
 */
class HomeAdapter(activity: Activity) : FSimpleRecyclerAdapter<Article>(activity) {

    override fun getLayoutId(parent: ViewGroup?, viewType: Int): Int = R.layout.item_home_list

    override fun onBindData(holder: FRecyclerViewHolder<Article>?, position: Int, model: Article?) {

    }
}