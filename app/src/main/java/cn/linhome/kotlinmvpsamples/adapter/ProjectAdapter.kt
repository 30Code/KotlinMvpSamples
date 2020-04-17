package cn.linhome.kotlinmvpsamples.adapter

import android.app.Activity
import android.text.Html
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.model.bean.Article
import cn.linhome.kotlinmvpsamples.utils.GlideUtil
import cn.linhome.lib.adapter.FSimpleRecyclerAdapter
import cn.linhome.lib.adapter.viewholder.FRecyclerViewHolder
import org.jetbrains.anko.imageResource

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/17
 */
class ProjectAdapter(activity: Activity) : FSimpleRecyclerAdapter<Article>(activity) {

    private var mCallBack : CallBack? = null

    open fun setBackCall(callBack : CallBack) {
        this.mCallBack = callBack
    }

    override fun getLayoutId(parent: ViewGroup?, viewType: Int): Int = R.layout.item_project_list

    override fun onBindData(holder: FRecyclerViewHolder<Article>?, position: Int, model: Article?) {
        val tv_item_project_list_title = holder!!.get<View>(R.id.tv_item_project_list_title) as TextView
        val tv_item_project_list_content = holder!!.get<View>(R.id.tv_item_project_list_content) as TextView
        val tv_item_project_list_time = holder!!.get<View>(R.id.tv_item_project_list_time) as TextView
        val iv_item_project_list_like = holder!!.get<View>(R.id.iv_item_project_list_like) as ImageView
        val tv_item_project_list_author = holder!!.get<View>(R.id.tv_item_project_list_author) as TextView
        val iv_item_project_list = holder!!.get<View>(R.id.iv_item_project_list) as ImageView

        tv_item_project_list_title.text = Html.fromHtml(model?.title)
        tv_item_project_list_content.text = Html.fromHtml(model?.desc)
        val authorStr = if (model!!.author.isNotEmpty()) model?.author else model?.shareUser
        tv_item_project_list_author.text = authorStr
        tv_item_project_list_time.text = model?.niceDate
        iv_item_project_list_like.imageResource = if (model?.collect) R.drawable.ic_like else R.drawable.ic_like_not

        GlideUtil.load(model?.envelopePic)?.into(iv_item_project_list)

        iv_item_project_list_like.setOnClickListener {
            mCallBack?.isCollectArticle(position, model)
        }

        holder.itemView.setOnClickListener {
            notifyItemClickCallback(model, it)
        }
    }

    interface CallBack {
        fun isCollectArticle(position: Int, model: Article)
    }
}