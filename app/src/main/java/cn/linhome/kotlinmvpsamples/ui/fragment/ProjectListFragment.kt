package cn.linhome.kotlinmvpsamples.ui.fragment

import android.os.Bundle
import android.view.View
import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.adapter.ProjectAdapter
import cn.linhome.kotlinmvpsamples.base.BaseMvpListFragment
import cn.linhome.kotlinmvpsamples.constant.Constant
import cn.linhome.kotlinmvpsamples.model.bean.Article
import cn.linhome.kotlinmvpsamples.model.bean.ArticleResponseBody
import cn.linhome.kotlinmvpsamples.mvp.contract.ProjectListContract
import cn.linhome.kotlinmvpsamples.mvp.presenter.ProjectListPresenter
import cn.linhome.kotlinmvpsamples.ui.activity.WanWebViewActivity
import cn.linhome.lib.adapter.callback.ItemClickCallback
import cn.linhome.lib.receiver.FNetworkReceiver
import cn.linhome.lib.utils.context.FToast
import kotlinx.android.synthetic.main.frag_square.*
import org.jetbrains.anko.support.v4.startActivity

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/17
 */
class ProjectListFragment : BaseMvpListFragment<ProjectListContract.View, ProjectListContract.Presenter>(), ProjectListContract.View {

    companion object {
        fun getInstance(cid: Int): ProjectListFragment {
            val fragment = ProjectListFragment()
            val args = Bundle()
            args.putInt(Constant.CONTENT_CID_KEY, cid)
            fragment.arguments = args
            return fragment
        }
    }

    private var mCid : Int = 0

    private lateinit var mProjectAdapter : ProjectAdapter

    override fun createPresenter(): ProjectListContract.Presenter = ProjectListPresenter()

    override fun onCreateContentView(): Int = R.layout.frag_project_list

    override fun initView(view: View) {
        super.initView(view)
        mLayoutStatusView = multiple_status_view
        mCid = arguments?.getInt(Constant.CONTENT_CID_KEY) ?: -1

        mProjectAdapter = ProjectAdapter(baseActivity)
        rv_list.adapter = mProjectAdapter

        mProjectAdapter.setItemClickCallback(ItemClickCallback { position, item, view ->
            startActivity<WanWebViewActivity>(Pair(Constant.EXTRA_URL, item.link))
        })

        mProjectAdapter.setBackCall(object : ProjectAdapter.CallBack {
            override fun isCollectArticle(position: Int, model: Article) {
                if (mIsLogin) {
                    if (!FNetworkReceiver.isNetworkConnected(context)) {
                        FToast.show(getString(R.string.no_network))
                        return
                    }
                    val collect = model.collect
                    model.collect = !collect
                    mProjectAdapter.dataHolder.updateData(position, model)
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
    }

    override fun lazyLoad() {
        mLayoutStatusView?.showLoading()
        mPresenter?.requestProjectList(1, mCid)
    }

    override fun onRefreshList() {
        mPresenter?.requestProjectList(1, mCid)
    }

    override fun onLoadMoreList() {
        val page = mProjectAdapter.itemCount / mPageSize + 1
        mPresenter?.requestProjectList(page, mCid)
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

    override fun setProjectList(articles: ArticleResponseBody) {
        articles.datas.let {
            mProjectAdapter.run {
                if (mIsRefresh) {
                    mProjectAdapter.dataHolder.data = it
                } else {
                    mProjectAdapter.dataHolder.appendData(it)
                }
            }
        }

        if (mProjectAdapter.itemCount == 0) {
            mLayoutStatusView?.showEmpty()
        } else {
            mLayoutStatusView?.showContent()
        }

        getPullToRefreshViewWrapper()?.stopRefreshing()
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