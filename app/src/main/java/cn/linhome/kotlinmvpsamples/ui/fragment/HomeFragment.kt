package cn.linhome.kotlinmvpsamples.ui.fragment

import android.view.View
import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.adapter.HomeAdapter
import cn.linhome.kotlinmvpsamples.base.BaseMvpFragment
import cn.linhome.kotlinmvpsamples.model.bean.Article
import cn.linhome.kotlinmvpsamples.model.bean.ArticleResponseBody
import cn.linhome.kotlinmvpsamples.model.bean.Banner
import cn.linhome.kotlinmvpsamples.mvp.contract.HomeContract
import cn.linhome.kotlinmvpsamples.mvp.presenter.HomePresenter
import cn.linhome.kotlinmvpsamples.view.pulltorefresh.IPullToRefreshViewWrapper
import kotlinx.android.synthetic.main.frag_home.*

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/6
 */
class HomeFragment : BaseMvpFragment<HomeContract.View, HomeContract.Presenter>(), HomeContract.View{

    companion object {
        fun getInstance(): HomeFragment = HomeFragment()
    }

    /**
     * datas
     */
    private val mDatas = mutableListOf<Article>()

    /**
     * banner datas
     */
    private lateinit var mBannerDatas: ArrayList<Banner>

    private var mIsRefresh = true

    private lateinit var mHomeAdapter: HomeAdapter

    override fun createPresenter(): HomeContract.Presenter = HomePresenter()

    override fun onCreateContentView(): Int = R.layout.frag_home

    override fun initView(view: View) {
        mHomeAdapter = HomeAdapter(baseActivity)
        rv_home.adapter = mHomeAdapter

        getPullToRefreshViewWrapper()?.setOnRefreshCallbackWrapper(object :
            IPullToRefreshViewWrapper.OnRefreshCallbackWrapper {

            override fun onRefreshingFromHeader() {
                mIsRefresh = true
                mPresenter?.requestHomeData()
            }

            override fun onRefreshingFromFooter() {
                mIsRefresh = false
                val page = mHomeAdapter.itemCount / 20
                mPresenter?.requestArticles(page)
            }
        })
    }

    override fun lazyLoad() {
        mPresenter?.requestHomeData()
    }

    override fun scrollToTop() {

    }

    override fun setBanner(banners: List<Banner>) {

    }

    override fun setArticles(articles: ArticleResponseBody) {
        articles.datas.let {
            mHomeAdapter.run {
                if (mIsRefresh) {
                    dataHolder.data = it
                } else {
                    dataHolder.appendData(it)
                }
            }
        }
        getPullToRefreshViewWrapper()?.stopRefreshing()
    }

    override fun showError(errorMsg: String) {
        super.showError(errorMsg)
        getPullToRefreshViewWrapper()?.stopRefreshing()
    }

    override fun showCollectSuccess(success: Boolean) {

    }

    override fun showCancelCollectSuccess(success: Boolean) {

    }
}