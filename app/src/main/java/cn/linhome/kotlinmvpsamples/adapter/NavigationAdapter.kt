package cn.linhome.kotlinmvpsamples.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.appview.TagFlexBoxView
import cn.linhome.kotlinmvpsamples.model.bean.Article
import cn.linhome.kotlinmvpsamples.model.bean.NavigationBean
import cn.linhome.lib.adapter.FSimpleRecyclerAdapter
import cn.linhome.lib.adapter.viewholder.FRecyclerViewHolder

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/16
 */
class NavigationAdapter : FSimpleRecyclerAdapter<NavigationBean> {

    constructor(activity: Activity?) : super(activity) {

    }

    override fun getLayoutId(parent: ViewGroup?, viewType: Int): Int = R.layout.item_navigation_list

    override fun onBindData(holder: FRecyclerViewHolder<NavigationBean>, position: Int, model: NavigationBean?) {
        val tv_item_navigation = holder.get<View>(R.id.tv_item_navigation) as TextView
        val view_tag = holder.get<View>(R.id.view_tag) as TagFlexBoxView<Article>

        tv_item_navigation.text = model?.name

        view_tag.getAdapter()?.dataHolder?.data = model?.articles
    }
}