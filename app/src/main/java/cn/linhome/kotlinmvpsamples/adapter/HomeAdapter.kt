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
 *  date : 2020/4/7
 */
class HomeAdapter(activity: Activity) : FSimpleRecyclerAdapter<Article>(activity) {

    override fun getLayoutId(parent: ViewGroup?, viewType: Int): Int = R.layout.item_home_list

    override fun onBindData(holder: FRecyclerViewHolder<Article>?, position: Int, model: Article?) {
        val tv_article_title = holder!!.get<View>(R.id.tv_article_title) as TextView
        val tv_article_author = holder!!.get<View>(R.id.tv_article_author) as TextView
        val tv_article_date = holder!!.get<View>(R.id.tv_article_date) as TextView
        val iv_like = holder!!.get<View>(R.id.iv_like) as ImageView
        val tv_article_chapterName = holder!!.get<View>(R.id.tv_article_chapterName) as TextView
        val iv_article_thumbnail = holder!!.get<View>(R.id.iv_article_thumbnail) as ImageView
        val tv_article_fresh = holder!!.get<View>(R.id.tv_article_fresh) as TextView
        val tv_article_top = holder!!.get<View>(R.id.tv_article_top) as TextView
        val tv_article_tag = holder!!.get<View>(R.id.tv_article_tag) as TextView

        tv_article_title.text = Html.fromHtml(model?.title)
        val authorStr = if (model!!.author.isNotEmpty()) model?.author else model?.shareUser
        tv_article_author.text = authorStr
        tv_article_date.text = model?.niceDate
        iv_like.imageResource = if (model?.collect) R.drawable.ic_like else R.drawable.ic_like_not

        val chapterName = when {
            model?.superChapterName.isNotEmpty() and model?.chapterName.isNotEmpty() ->
                "${model?.superChapterName} / ${model?.chapterName}"
            model?.superChapterName.isNotEmpty() -> model?.superChapterName
            model?.chapterName.isNotEmpty() -> model?.chapterName
            else -> ""
        }

        tv_article_chapterName.text = chapterName
        if (model.envelopePic.isNotEmpty()) {
            iv_article_thumbnail.visibility = View.VISIBLE
            GlideUtil.load(model?.envelopePic)?.into(iv_article_thumbnail)
        } else {
            iv_article_thumbnail.visibility = View.GONE
        }

        if (model?.top == "1") {
            tv_article_top.visibility = View.VISIBLE
        } else {
            tv_article_top.visibility = View.GONE
        }

        if (model?.tags.size > 0) {
            tv_article_tag.visibility = View.VISIBLE
            tv_article_tag.text = model?.tags[0].name
        } else {
            tv_article_tag.visibility = View.GONE
        }

        iv_like.setOnClickListener {

        }

        holder.itemView.setOnClickListener {
            notifyItemClickCallback(model, it)
        }
    }
}