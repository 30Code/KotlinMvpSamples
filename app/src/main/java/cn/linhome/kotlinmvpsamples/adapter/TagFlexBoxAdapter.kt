package cn.linhome.kotlinmvpsamples.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.interfaces.GetTabTagInterface
import cn.linhome.kotlinmvpsamples.utils.CommonUtil
import cn.linhome.lib.adapter.FSimpleRecyclerAdapter
import cn.linhome.lib.adapter.viewholder.FRecyclerViewHolder

/**
 * des :
 * Created by 30Code
 * date : 2020/4/16
 */
class TagFlexBoxAdapter<T : GetTabTagInterface?> : FSimpleRecyclerAdapter<T> {

    private var mLimit : Boolean = false//限制4个 = false

    @LayoutRes
    private var mLayoutId = -1

    constructor(activity: Activity?) : super(activity) {}

    constructor(activity: Activity?, @LayoutRes mLayoutId: Int) : super(activity) {
        this.mLayoutId = mLayoutId
    }

    override fun getItemCount(): Int {
        return if (mLimit) {
            if (dataHolder.size() > 4) 4 else dataHolder.size()
        } else {
            super.getItemCount()
        }
    }

    fun setLimit(limit: Boolean) {
        mLimit = limit
    }

    override fun getLayoutId(parent: ViewGroup, viewType: Int): Int {
        return if (mLayoutId != -1) {
            mLayoutId
        } else R.layout.item_tag_flex_box
    }

    override fun onBindData(holder: FRecyclerViewHolder<T>, position: Int, model: T) {
        val tv_title = holder.get<View>(R.id.tv_title) as TextView
        if (null != model) {
            tv_title.text = model.getTabTagText()
            tv_title.setTextColor(CommonUtil.randomColor())
        }

        holder.itemView.setOnClickListener { v -> notifyItemClickCallback(model, v) }
    }
}