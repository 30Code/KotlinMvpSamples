package cn.linhome.kotlinmvpsamples.ui.fragment

import android.view.View
import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.adapter.HomeAdapter
import cn.linhome.kotlinmvpsamples.base.BaseMvpListFragment
import cn.linhome.kotlinmvpsamples.constant.Constant
import cn.linhome.kotlinmvpsamples.model.bean.Article
import cn.linhome.kotlinmvpsamples.model.bean.ArticleResponseBody
import cn.linhome.kotlinmvpsamples.mvp.contract.SquareContract
import cn.linhome.kotlinmvpsamples.mvp.presenter.SquarePresenter
import cn.linhome.kotlinmvpsamples.ui.activity.WanWebViewActivity
import cn.linhome.lib.adapter.callback.ItemClickCallback
import cn.linhome.lib.receiver.FNetworkReceiver
import cn.linhome.lib.utils.context.FToast
import kotlinx.android.synthetic.main.frag_square.*
import org.jetbrains.anko.support.v4.startActivity

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/6
 */
class SquareFragment : BaseMvpListFragment<SquareContract.View, SquarePresenter>(), SquareContract.View{

    companion object {
        fun getInstance(): SquareFragment = SquareFragment()
    }

    private lateinit var mHomeAdapter: HomeAdapter

    override fun createPresenter(): SquarePresenter = SquarePresenter()

    override fun onCreateContentView(): Int = R.layout.frag_square

    override fun initView(view: View) {
        super.initView(view)
        mHomeAdapter = HomeAdapter(baseActivity)
        rv_list.adapter = mHomeAdapter

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
                }
            }
        })
    }

    override fun hideLoading() {
        super.hideLoading()
        if (mIsRefresh) {

        }
    }

    override fun lazyLoad() {
        mPresenter?.getSquareList(0)
    }

    override fun onRefreshList() {
        mPresenter?.getSquareList(0)
    }

    override fun onLoadMoreList() {
        val page = mHomeAdapter.itemCount / mPageSize
        mPresenter?.getSquareList(page)
    }

    override fun scrollToTop() {
        rv_list.run {
            if (linearLayoutManager.findFirstVisibleItemPosition() > 20) {
                scrollToPosition(0)
            } else {
                smoothScrollToPosition(0)
            }
        }
    }

    override fun showSquareList(body: ArticleResponseBody) {
        body.datas.let {
            mHomeAdapter.run {
                if (mIsRefresh) {
                    mHomeAdapter.dataHolder.data = it
                } else {
                    mHomeAdapter.dataHolder.appendData(it)
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