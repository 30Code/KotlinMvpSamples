package cn.linhome.kotlinmvpsamples.appview

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.adapter.TagFlexBoxAdapter
import cn.linhome.kotlinmvpsamples.base.BaseAppView
import cn.linhome.kotlinmvpsamples.interfaces.GetTabTagInterface
import cn.linhome.lib.utils.context.FResUtil
import cn.linhome.lib.utils.extend.FDrawable
import cn.linhome.library.view.SDRecyclerView
import com.google.android.flexbox.FlexboxLayoutManager

/**
 * des :
 * Created by 30Code
 * date : 2020/4/22
 */
class TagFlexBoxView<T : GetTabTagInterface?> : BaseAppView {

    var mRecyclerView: SDRecyclerView? = null

    var mTagFlexBoxAdapter: TagFlexBoxAdapter<T>? = null

    private var mIsSetStyle : Boolean = false//只设置一次 = false

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) { init() }

    private fun init() {
        mRecyclerView = SDRecyclerView(activity)
        mRecyclerView!!.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        setContentView(mRecyclerView)
        mRecyclerView!!.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        mRecyclerView!!.layoutManager = FlexboxLayoutManager(activity)
        mTagFlexBoxAdapter = TagFlexBoxAdapter(activity)
        mRecyclerView!!.adapter = mTagFlexBoxAdapter
    }

    open fun getAdapter(): TagFlexBoxAdapter<T>? {
        return mTagFlexBoxAdapter
    }

    /**
     * 搜索样式
     */
    fun setSearchStyle() {
        if (mIsSetStyle) return
        mIsSetStyle = true
        mRecyclerView!!.addDividerVertical(FDrawable().size(FResUtil.dp2px(1f)).color(resources.getColor(R.color.transparent)))
        mRecyclerView!!.addDividerHorizontal(FDrawable().size(FResUtil.dp2px(1f)).color(resources.getColor(R.color.transparent)))
    }
}