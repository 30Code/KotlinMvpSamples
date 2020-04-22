package cn.linhome.kotlinmvpsamples.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.model.bean.SearchHistoryBean
import cn.linhome.lib.adapter.FSimpleRecyclerAdapter
import cn.linhome.lib.adapter.viewholder.FRecyclerViewHolder

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/22
 */
class SearchHistoryAdapter(activity: Activity?) : FSimpleRecyclerAdapter<SearchHistoryBean>(activity) {

    override fun getLayoutId(parent: ViewGroup?, viewType: Int): Int = R.layout.item_search_history

    override fun onBindData(holder: FRecyclerViewHolder<SearchHistoryBean>, position: Int, model: SearchHistoryBean) {
        val tv_search_key = holder.get<View>(R.id.tv_search_key) as TextView

        tv_search_key.text = model.key

        holder.itemView.setOnClickListener {
            notifyItemClickCallback(model, it)
        }
    }
}