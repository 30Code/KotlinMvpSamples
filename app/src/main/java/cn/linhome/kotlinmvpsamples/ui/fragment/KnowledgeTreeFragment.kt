package cn.linhome.kotlinmvpsamples.ui.fragment

import android.view.View
import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.adapter.KnowledgeTreeAdapter
import cn.linhome.kotlinmvpsamples.base.BaseMvpListFragment
import cn.linhome.kotlinmvpsamples.model.bean.KnowledgeTreeBody
import cn.linhome.kotlinmvpsamples.mvp.contract.KnowledgeTreeContract
import cn.linhome.kotlinmvpsamples.mvp.presenter.KnowledgeTreePresenter
import kotlinx.android.synthetic.main.frag_square.*

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/15
 */
class KnowledgeTreeFragment : BaseMvpListFragment<KnowledgeTreeContract.View, KnowledgeTreeContract.Presenter>(), KnowledgeTreeContract.View{

    private lateinit var mKnowledgeTreeAdapter : KnowledgeTreeAdapter

    override fun createPresenter(): KnowledgeTreeContract.Presenter = KnowledgeTreePresenter()

    override fun onCreateContentView(): Int = R.layout.frag_knowledge_tree

    override fun initView(view: View) {
        super.initView(view)
        mKnowledgeTreeAdapter = KnowledgeTreeAdapter(baseActivity)
        rv_list.adapter = mKnowledgeTreeAdapter
    }

    override fun lazyLoad() {
        mPresenter?.requestKnowledgeTree()
    }

    override fun onRefreshList() {
        mPresenter?.requestKnowledgeTree()
    }

    override fun onLoadMoreList() {
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

    override fun setKnowledgeTree(list: List<KnowledgeTreeBody>) {
        list.let {
            mKnowledgeTreeAdapter.dataHolder.data = it
        }
        getPullToRefreshViewWrapper()?.stopRefreshing()
    }

    override fun showError(errorMsg: String) {
        super.showError(errorMsg)
        getPullToRefreshViewWrapper()?.stopRefreshing()
    }
}