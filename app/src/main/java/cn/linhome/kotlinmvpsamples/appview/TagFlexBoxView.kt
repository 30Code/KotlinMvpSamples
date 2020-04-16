package cn.linhome.kotlinmvpsamples.appview

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.adapter.TagFlexBoxAdapter
import cn.linhome.kotlinmvpsamples.base.BaseAppView
import cn.linhome.kotlinmvpsamples.interfaces.GetTabTagInterface
import cn.linhome.lib.utils.extend.FDrawable
import cn.linhome.library.view.SDRecyclerView
import com.google.android.flexbox.FlexboxLayoutManager

/**
 *  des : 流式布局，用于标签
 *  Created by 30Code
 *  date : 2020/4/16
 */
class TagFlexBoxView<T : GetTabTagInterface> : BaseAppView{

    private lateinit var mRecyclerView : SDRecyclerView

    private lateinit var mTagFlexBoxAdapter : TagFlexBoxAdapter<T>

    private var mIsSetStyle : Boolean = false//只设置一次

    constructor(context: Context) : super(context)

    override fun initView() {
        mRecyclerView = SDRecyclerView(activity)
        mRecyclerView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        setContentView(mRecyclerView)
        mRecyclerView.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        mRecyclerView.layoutManager = FlexboxLayoutManager(activity)
        mTagFlexBoxAdapter = TagFlexBoxAdapter<T>(activity)
        mRecyclerView.adapter = mTagFlexBoxAdapter
    }

    fun getAdapter(): TagFlexBoxAdapter<T>? {
        return mTagFlexBoxAdapter
    }

    fun getRecyclerView(): SDRecyclerView? {
        return mRecyclerView
    }

    /**
     * 导航标签样式
     */
    fun setNavigationStyle() {
        if (mIsSetStyle) return
        mIsSetStyle = true
        mRecyclerView.addDividerVertical(
            FDrawable().size(resources.getDimension(R.dimen.res_px_21).toInt())
                .color(resources.getColor(R.color.transparent))
        )
        mRecyclerView.addDividerHorizontal(
            FDrawable().size(resources.getDimension(R.dimen.res_px_16).toInt())
                .color(resources.getColor(R.color.transparent))
        )
    }

    override fun start() {

    }
}