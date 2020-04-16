package cn.linhome.kotlinmvpsamples.ui.fragment

import android.view.View
import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.adapter.NavigationAdapter
import cn.linhome.kotlinmvpsamples.base.BaseFragment
import cn.linhome.kotlinmvpsamples.base.BaseMvpFragment
import cn.linhome.kotlinmvpsamples.model.bean.NavigationBean
import cn.linhome.kotlinmvpsamples.mvp.contract.NavigationContract
import cn.linhome.kotlinmvpsamples.mvp.presenter.NavigationPresenter
import kotlinx.android.synthetic.main.frag_navigation.*

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/15
 */
class NavigationFragment : BaseMvpFragment<NavigationContract.View, NavigationContract.Presenter>(), NavigationContract.View{

    private lateinit var mNavigationAdapter : NavigationAdapter

    override fun createPresenter(): NavigationContract.Presenter = NavigationPresenter()

    override fun onCreateContentView(): Int = R.layout.frag_navigation

    override fun initView(view: View) {
        super.initView(view)
        mNavigationAdapter = NavigationAdapter(baseActivity)
        fv_navigation.adapter = mNavigationAdapter
    }

    override fun lazyLoad() {
        mPresenter?.requestNavigationList()
    }

    override fun setNavigationData(list: List<NavigationBean>) {
        list.let {
            mNavigationAdapter.run {
                mNavigationAdapter.dataHolder.data = it
            }
        }
    }
}