package cn.linhome.kotlinmvpsamples.ui.fragment

import android.view.View
import android.widget.ImageView
import cn.bingoogolapple.bgabanner.BGABanner
import cn.linhome.headerfooter.songhang.library.SmartRecyclerAdapter
import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.adapter.HomeAdapter
import cn.linhome.kotlinmvpsamples.base.BaseMvpFragment
import cn.linhome.kotlinmvpsamples.constant.Constant
import cn.linhome.kotlinmvpsamples.model.bean.Article
import cn.linhome.kotlinmvpsamples.model.bean.ArticleResponseBody
import cn.linhome.kotlinmvpsamples.model.bean.Banner
import cn.linhome.kotlinmvpsamples.mvp.contract.HomeContract
import cn.linhome.kotlinmvpsamples.mvp.presenter.HomePresenter
import cn.linhome.kotlinmvpsamples.ui.activity.WanWebViewActivity
import cn.linhome.kotlinmvpsamples.utils.GlideUtil
import cn.linhome.kotlinmvpsamples.view.pulltorefresh.IPullToRefreshViewWrapper
import cn.linhome.lib.adapter.callback.ItemClickCallback
import cn.linhome.lib.receiver.FNetworkReceiver
import cn.linhome.lib.utils.context.FToast
import io.reactivex.Observable
import kotlinx.android.synthetic.main.frag_home.*
import kotlinx.android.synthetic.main.item_home_banner.view.*
import org.jetbrains.anko.support.v4.startActivity

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
     * banner datas
     */
    private lateinit var mBannerDatas: ArrayList<Banner>

    private var mIsRefresh = true

    /**
     * banner view
     */
    private var mBannerView: View? = null

    /**
     * Banner Adapter
     */
    private val mBannerAdapter: BGABanner.Adapter<ImageView, String> by lazy {
        BGABanner.Adapter<ImageView, String> { bgaBanner, imageView, feedImageUrl, position ->
            GlideUtil.load(feedImageUrl)?.into(imageView)
        }
    }

    private lateinit var mHomeAdapter: HomeAdapter

    override fun createPresenter(): HomeContract.Presenter = HomePresenter()

    override fun onCreateContentView(): Int = R.layout.frag_home

    override fun initView(view: View) {
        super.initView(view)

        mLayoutStatusView = multiple_status_view

        mBannerView = layoutInflater.inflate(R.layout.item_home_banner, null)
        mBannerView?.banner?.run {
            setDelegate(mBannerDelegate)
        }

        mHomeAdapter = HomeAdapter(baseActivity)
        val mSmartRecyclerAdapter = SmartRecyclerAdapter(mHomeAdapter)
        mSmartRecyclerAdapter.setHeaderView(mBannerView)
        rv_home.adapter = mSmartRecyclerAdapter

        mHomeAdapter.setItemClickCallback(ItemClickCallback { position, item, view ->
            startActivity<WanWebViewActivity>(Pair(Constant.EXTRA_URL, item.link))
        })

        mHomeAdapter.setBackCall(object : HomeAdapter.CallBack {
            override fun isCollectArticle(position: Int, model: Article) {
                if (mIsLogin) {
                    if (!FNetworkReceiver.isNetworkConnected(context)) {
                        FToast.show(getString(R.string.no_network))
                        return
                    }
                    val collect = model.collect
                    model.collect = !collect
                    mHomeAdapter.dataHolder.updateData(position, model)
                    if (collect) {
                        mPresenter?.cancelCollectArticle(model.id)
                    } else {
                        mPresenter?.addCollectArticle(model.id)
                    }
                } else {
                    //先登录
                    FToast.show("请先登录")
                }
            }
        })

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
        mLayoutStatusView?.showLoading()
        mPresenter?.requestHomeData()
    }

    override fun scrollToTop() {
        rv_home.run {
            if (linearLayoutManager.findFirstVisibleItemPosition() > 20) {
                scrollToPosition(0)
            } else {
                smoothScrollToPosition(0)
            }
        }
    }

    override fun setBanner(banners: List<Banner>) {
        mBannerDatas = banners as ArrayList<Banner>
        val bannerFeedList = ArrayList<String>()
        val bannerTitleList = ArrayList<String>()
        Observable.fromIterable(banners)
            .subscribe {list ->
                bannerFeedList.add(list.imagePath)
                bannerTitleList.add(list.title)
            }
        mBannerView?.banner?.run {
            setAutoPlayAble(bannerFeedList.size > 1)
            setData(bannerFeedList, bannerTitleList)
            setAdapter(mBannerAdapter)
        }
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

        if (mHomeAdapter.itemCount == 0) {
            mLayoutStatusView?.showEmpty()
        } else {
            mLayoutStatusView?.showContent()
        }

        getPullToRefreshViewWrapper()?.stopRefreshing()
    }

    /**
     * BannerClickListener
     */
    private val mBannerDelegate = BGABanner.Delegate<ImageView, String> { banner, imageView, model, position ->
        if (mBannerDatas.size > 0) {
            val data = mBannerDatas[position]
            startActivity<WanWebViewActivity>(Pair(Constant.EXTRA_URL, data.url))
        }
    }

    override fun showError(errorMsg: String) {
        super.showError(errorMsg)
        mLayoutStatusView?.showError()
        getPullToRefreshViewWrapper()?.stopRefreshing()
    }

    override fun showCollectSuccess(success: Boolean) {

    }

    override fun showCancelCollectSuccess(success: Boolean) {

    }
}