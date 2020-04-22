package cn.linhome.kotlinmvpsamples.ui.activity

import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.SearchView
import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.base.BaseMvpActivity
import cn.linhome.kotlinmvpsamples.model.bean.HotSearchBean
import cn.linhome.kotlinmvpsamples.model.bean.SearchHistoryBean
import cn.linhome.kotlinmvpsamples.mvp.contract.SearchContract
import cn.linhome.kotlinmvpsamples.mvp.presenter.SearchPresenter
import kotlinx.android.synthetic.main.act_search.*

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/22
 */
class SearchActivity : BaseMvpActivity<SearchContract.View, SearchContract.Presenter>(), SearchContract.View {

    override fun createdPresenter(): SearchContract.Presenter = SearchPresenter()

    override fun onCreateContentView(): Int = R.layout.act_search

    override fun initView() {
        super.initView()
        toolbar.run {
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_search, menu)
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.onActionViewExpanded()
        searchView.queryHint = getString(R.string.search_tint)
        searchView.setOnQueryTextListener(mQueryTextListener)
        searchView.isSubmitButtonEnabled = true

        try {
            val field = searchView.javaClass.getDeclaredField("mGoButton")
            field.isAccessible = true
            val goButton = field.get(searchView) as ImageView
            goButton.setImageResource(R.drawable.ic_search_white_24dp)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return super.onCreateOptionsMenu(menu)
    }

    private val mQueryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
//            goToSearchList(query.toString())
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun start() {
        mPresenter?.getHotSearchData()
    }

    override fun showHistoryData(listHistoryBean: MutableList<SearchHistoryBean>) {
    }

    override fun showHotSearchData(listHotBean: MutableList<HotSearchBean>) {
        view_hot_search.getAdapter()?.dataHolder?.data = listHotBean
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showDefaultMsg(msg: String) {
    }

    override fun showMsg(msg: String) {
    }

    override fun showError(errorMsg: String) {
    }

}