package cn.linhome.kotlinmvpsamples.ui.fragment

import android.view.View
import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.adapter.HomeAdapter
import cn.linhome.kotlinmvpsamples.base.BaseMvpListFragment
import cn.linhome.kotlinmvpsamples.model.bean.ArticleResponseBody
import cn.linhome.kotlinmvpsamples.mvp.contract.SquareContract
import cn.linhome.kotlinmvpsamples.mvp.presenter.SquarePresenter
import kotlinx.android.synthetic.main.frag_square.*

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
    }

    override fun showCollectSuccess(success: Boolean) {
    }

    override fun showCancelCollectSuccess(success: Boolean) {
    }
}