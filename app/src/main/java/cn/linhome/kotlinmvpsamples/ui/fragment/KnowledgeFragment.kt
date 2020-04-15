package cn.linhome.kotlinmvpsamples.ui.fragment

import android.os.Bundle
import android.view.View
import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.adapter.KnowledgeAdapter
import cn.linhome.kotlinmvpsamples.base.BaseMvpListFragment
import cn.linhome.kotlinmvpsamples.constant.Constant
import cn.linhome.kotlinmvpsamples.model.bean.Article
import cn.linhome.kotlinmvpsamples.model.bean.ArticleResponseBody
import cn.linhome.kotlinmvpsamples.mvp.contract.KnowledgeContract
import cn.linhome.kotlinmvpsamples.mvp.presenter.KnowledgePresenter
import cn.linhome.kotlinmvpsamples.ui.activity.WanWebViewActivity
import cn.linhome.lib.adapter.callback.ItemClickCallback
import cn.linhome.lib.receiver.FNetworkReceiver
import cn.linhome.lib.utils.context.FToast
import kotlinx.android.synthetic.main.frag_square.*
import org.jetbrains.anko.support.v4.startActivity

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/15
 */
class KnowledgeFragment : BaseMvpListFragment<KnowledgeContract.View, KnowledgeContract.Presenter>(), KnowledgeContract.View {

    companion object {
        fun getInstance(cid : Int) : KnowledgeFragment {
            val fragment = KnowledgeFragment()
            val args = Bundle()
            args.putInt(Constant.CONTENT_CID_KEY, cid)
            fragment.arguments = args
            return fragment
        }
    }

    private var mCid : Int = 0

    private lateinit var mKnowledgeAdapter: KnowledgeAdapter

    override fun createPresenter(): KnowledgeContract.Presenter = KnowledgePresenter()

    override fun initView(view: View) {
        super.initView(view)

        mCid = arguments?.getInt(Constant.CONTENT_CID_KEY, 0)!!

        mKnowledgeAdapter = KnowledgeAdapter(baseActivity)
        rv_list.adapter = mKnowledgeAdapter

        mKnowledgeAdapter.setItemClickCallback(ItemClickCallback { position, item, view ->
            startActivity<WanWebViewActivity>(Pair(Constant.EXTRA_URL, item.link))
        })

        mKnowledgeAdapter.setBackCall(object : KnowledgeAdapter.CallBack {
            override fun isCollectArticle(position: Int, model: Article) {
                if (mIsLogin) {
                    if (!FNetworkReceiver.isNetworkConnected(context)) {
                        FToast.show(getString(R.string.no_network))
                        return
                    }
                    val collect = model.collect
                    model.collect = !collect
                    mKnowledgeAdapter.dataHolder.updateData(position, model)
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
        mPresenter?.requestKnowledgeList(0, mCid)
    }

    override fun onCreateContentView(): Int  = R.layout.frag_square

    override fun onRefreshList() {
        mPresenter?.requestKnowledgeList(0, mCid)
    }

    override fun onLoadMoreList() {
        val page = mKnowledgeAdapter.itemCount / mPageSize
        mPresenter?.requestKnowledgeList(page, mCid)
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

    override fun setKnowledgeList(articles: ArticleResponseBody) {
        articles.datas.let {
            mKnowledgeAdapter.run {
                if (mIsRefresh) {
                    mKnowledgeAdapter.dataHolder.data = it
                } else {
                    mKnowledgeAdapter.dataHolder.appendData(it)
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