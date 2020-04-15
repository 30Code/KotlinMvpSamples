package cn.linhome.kotlinmvpsamples.ui.fragment

import android.graphics.Color
import android.view.View
import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.base.BaseMvpFragment
import cn.linhome.kotlinmvpsamples.mvp.contract.SystemContract
import cn.linhome.kotlinmvpsamples.mvp.presenter.SystemPresenter
import cn.linhome.lib.select.view.extend.FTabUnderline
import cn.linhome.lib.selectionmanager.FSelectManager
import cn.linhome.lib.selectionmanager.FSelectViewManager
import cn.linhome.lib.utils.context.FResUtil
import kotlinx.android.synthetic.main.frag_system.*

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/6
 */
class SystemFragment : BaseMvpFragment<SystemContract.View, SystemContract.Presenter>(), SystemContract.View{

    companion object {
        fun getInstance(): SystemFragment = SystemFragment()
    }

    private val mSelectManager : FSelectViewManager<FTabUnderline> = FSelectViewManager<FTabUnderline>()

    override fun onCreateContentView(): Int = R.layout.frag_system

    override fun initView(view: View) {
        super.initView(view)
        setTabUnderLine(tab_system, getString(R.string.knowledge_system))
        setTabUnderLine(tab_navigation, getString(R.string.navigation))

        mSelectManager.addCallback(object : FSelectManager.Callback<FTabUnderline> {
            override fun onSelected(item: FTabUnderline?) {
                val index : Int = mSelectManager.indexOf(item)
                if (index == 0) {
                    sdFragmentManager.toggle(R.id.fl_content, null, KnowledgeTreeFragment::class.java)
                } else if (index == 1) {
                    sdFragmentManager.toggle(R.id.fl_content, null, NavigationFragment::class.java)
                }
            }

            override fun onNormal(item: FTabUnderline?) {

            }

        })

        mSelectManager.setItems(tab_system, tab_navigation)
    }

    fun setTabUnderLine(tabUnderLine: FTabUnderline, title : String){
        tabUnderLine.tv_text.setText(title)
        tabUnderLine.configText(tabUnderLine.tv_text)
            .setTextColorResIdNormal(R.color.res_bg_activity)
            .setTextColorResIdSelected(R.color.white)
        tabUnderLine.config(tabUnderLine.view_underline)
            .setBackgroundColorNormal(Color.TRANSPARENT)
            .setBackgroundColorSelected(R.color.white)
            .setWidthNormal(FResUtil.dp2px(70f))
    }

    override fun lazyLoad() {

    }

    override fun createPresenter(): SystemContract.Presenter =SystemPresenter()

    override fun scrollToTop() {
    }
}