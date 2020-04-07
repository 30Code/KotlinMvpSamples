package cn.linhome.kotlinmvpsamples.ui.fragment

import android.view.View
import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.base.BaseMvpFragment
import cn.linhome.kotlinmvpsamples.model.bean.Article
import cn.linhome.kotlinmvpsamples.model.bean.ArticleResponseBody
import cn.linhome.kotlinmvpsamples.model.bean.Banner
import cn.linhome.kotlinmvpsamples.mvp.contract.HomeContract
import cn.linhome.kotlinmvpsamples.mvp.presenter.HomePresenter

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
    private val datas = mutableListOf<Article>()

    /**
     * banner datas
     */
    private lateinit var bannerDatas: ArrayList<Banner>

    override fun createPresenter(): HomeContract.Presenter = HomePresenter()

    override fun onCreateContentView(): Int = R.layout.frag_home

    override fun initView(view: View) {

    }

    override fun lazyLoad() {
        mPresenter?.requestHomeData()
    }

    override fun scrollToTop() {

    }

    override fun setBanner(banners: List<Banner>) {

    }

    override fun setArticles(articles: ArticleResponseBody) {

    }

    override fun showCollectSuccess(success: Boolean) {

    }

    override fun showCancelCollectSuccess(success: Boolean) {

    }
}