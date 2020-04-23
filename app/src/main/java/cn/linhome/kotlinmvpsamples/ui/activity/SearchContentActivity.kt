package cn.linhome.kotlinmvpsamples.ui.activity

import androidx.recyclerview.widget.DefaultItemAnimator
import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.adapter.HomeAdapter
import cn.linhome.kotlinmvpsamples.base.BaseMvpActivity
import cn.linhome.kotlinmvpsamples.constant.Constant
import cn.linhome.kotlinmvpsamples.model.bean.Article
import cn.linhome.kotlinmvpsamples.model.bean.ArticleResponseBody
import cn.linhome.kotlinmvpsamples.mvp.contract.SearchContentContract
import cn.linhome.kotlinmvpsamples.mvp.presenter.SearchContentPresenter
import cn.linhome.kotlinmvpsamples.view.pulltorefresh.IPullToRefreshViewWrapper
import cn.linhome.lib.adapter.callback.ItemClickCallback
import cn.linhome.lib.receiver.FNetworkReceiver
import cn.linhome.lib.utils.context.FPreferencesUtil
import cn.linhome.lib.utils.context.FResUtil
import cn.linhome.lib.utils.context.FToast
import cn.linhome.lib.utils.extend.FDrawable
import kotlinx.android.synthetic.main.act_search_content.*
import org.jetbrains.anko.startActivity

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/23
 */
class SearchContentActivity : BaseMvpActivity<SearchContentContract.View, SearchContentContract.Presenter>(), SearchContentContract.View{

    private var mKey : String = ""

    /**
     * 每页数据的个数
     */
    private var mPageSize = 20

    /**
     * 是否是下拉刷新
     */
    private var mIsRefresh = true

    private lateinit var mAdapter : HomeAdapter

    override fun createdPresenter(): SearchContentContract.Presenter = SearchContentPresenter()

    override fun onCreateContentView(): Int = R.layout.act_search_content

    override fun initView() {
        super.initView()

        showTitle(true)
        mKey = intent.getStringExtra(Constant.EXTRA_SEARCH_KEY)

        getTitleView().itemMiddle.setTextTop(mKey)

        mLayoutStatusView = multiple_status_view

        mAdapter = HomeAdapter(this)
        rv_search.run {
            itemAnimator = DefaultItemAnimator()
            addDividerHorizontal(FDrawable().size(FResUtil.dp2px(1f)).color(resources.getColor(R.color.res_bg_activity)))
            adapter = mAdapter
        }

        mAdapter.setItemClickCallback(ItemClickCallback { position, item, view ->
            startActivity<WanWebViewActivity>(Pair(Constant.EXTRA_URL, item.link))
        })

        mAdapter.setBackCall(object : HomeAdapter.CallBack {
            override fun isCollectArticle(position: Int, model: Article) {
                var isLogin = FPreferencesUtil.getBoolean(Constant.LOGIN_KEY, false)
                if (isLogin) {
                    if (!FNetworkReceiver.isNetworkConnected(applicationContext)) {
                        FToast.show(getString(R.string.no_network))
                        return
                    }
                    val collect = model.collect
                    model.collect = !collect
                    mAdapter.dataHolder.updateData(position, model)
                    if (collect) {
                        mPresenter?.cancelCollectArticle(model.id)
                    } else {
                        mPresenter?.addCollectArticle(model.id)
                    }
                } else {
                    //先登录
                    FToast.show("请先登录")
                    startActivity<LoginActivity>()
                }
            }
        })

        getPullToRefreshViewWrapper()?.setOnRefreshCallbackWrapper(object :
            IPullToRefreshViewWrapper.OnRefreshCallbackWrapper {

            override fun onRefreshingFromHeader() {
                mIsRefresh = true
                onRefreshList()
            }

            override fun onRefreshingFromFooter() {
                mIsRefresh = false
                onLoadMoreList()
            }
        })
    }

    override fun start() {
        mPresenter?.queryBySearchKey(0, mKey)
    }

    fun onRefreshList() {
        mPresenter?.queryBySearchKey(0, mKey)
    }

    fun onLoadMoreList() {
        val page = mAdapter.itemCount / mPageSize
        mPresenter?.queryBySearchKey(page, mKey)
    }

    override fun showArticles(articles: ArticleResponseBody) {
        articles.datas.let {
            mAdapter.run {
                if (mIsRefresh) {
                    dataHolder.data = it
                } else {
                    dataHolder.appendData(it)
                }
            }
        }

        if (mAdapter.itemCount == 0) {
            mLayoutStatusView?.showEmpty()
        } else {
            mLayoutStatusView?.showContent()
        }

        getPullToRefreshViewWrapper()?.stopRefreshing()
    }

    override fun scrollToTop() {
    }

    override fun showCollectSuccess(success: Boolean) {
        if (success) {
            FToast.show(getString(R.string.collect_success))
        }
    }

    override fun showCancelCollectSuccess(success: Boolean) {
        if (success) {
            FToast.show(getString(R.string.cancel_collect_success))
        }
    }

    override fun showLoading() {
        mLayoutStatusView?.showLoading()
    }

    override fun hideLoading() {

    }

    override fun showDefaultMsg(msg: String) {
        FToast.show(msg)
    }

    override fun showMsg(msg: String) {
    }

    override fun showError(errorMsg: String) {
    }
}