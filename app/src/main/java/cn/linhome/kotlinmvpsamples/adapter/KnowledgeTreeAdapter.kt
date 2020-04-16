package cn.linhome.kotlinmvpsamples.adapter

import android.app.Activity
import android.text.Html
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.model.bean.KnowledgeTreeBody
import cn.linhome.lib.adapter.FSimpleRecyclerAdapter
import cn.linhome.lib.adapter.viewholder.FRecyclerViewHolder

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/16
 */
class KnowledgeTreeAdapter(activity: Activity) : FSimpleRecyclerAdapter<KnowledgeTreeBody>(activity){

    override fun getLayoutId(parent: ViewGroup?, viewType: Int): Int = R.layout.item_knowledge_tree_list

    override fun onBindData(holder: FRecyclerViewHolder<KnowledgeTreeBody>?, position: Int, model: KnowledgeTreeBody?
    ) {
        val tv_title_first = holder!!.get<View>(R.id.tv_title_first) as TextView
        val tv_title_second = holder!!.get<View>(R.id.tv_title_second) as TextView

        tv_title_first.text = model?.name
        model?.children.let {
            tv_title_second.text = it?.joinToString("    ", transform = { child ->
                Html.fromHtml(child.name)
            })
        }

        holder.itemView.setOnClickListener {
            notifyItemClickCallback(model, it)
        }
    }
}