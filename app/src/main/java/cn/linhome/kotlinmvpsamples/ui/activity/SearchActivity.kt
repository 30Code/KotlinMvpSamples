package cn.linhome.kotlinmvpsamples.ui.activity

import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView.OnEditorActionListener
import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.adapter.SearchHistoryAdapter
import cn.linhome.kotlinmvpsamples.base.BaseMvpActivity
import cn.linhome.kotlinmvpsamples.constant.Constant
import cn.linhome.kotlinmvpsamples.model.bean.HotSearchBean
import cn.linhome.kotlinmvpsamples.model.bean.SearchHistoryBean
import cn.linhome.kotlinmvpsamples.mvp.contract.SearchContract
import cn.linhome.kotlinmvpsamples.mvp.presenter.SearchPresenter
import cn.linhome.lib.utils.FCollectionUtil
import cn.linhome.lib.utils.FKeyboardUtil
import kotlinx.android.synthetic.main.act_search.*
import org.jetbrains.anko.editText
import org.jetbrains.anko.startActivity

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/22
 */
class SearchActivity : BaseMvpActivity<SearchContract.View, SearchContract.Presenter>(), SearchContract.View {

    private var mSearchKey : String = ""

    private lateinit var mSearchHistoryAdapter : SearchHistoryAdapter

    override fun createdPresenter(): SearchContract.Presenter = SearchPresenter()

    override fun onCreateContentView(): Int = R.layout.act_search

    override fun onResume() {
        super.onResume()
        mPresenter?.queryHistory()
    }

    override fun initView() {
        super.initView()
        toolbar.run {
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        view_hot_search.getAdapter()?.setItemClickCallback { position, getTabTagInterface, view ->
            goToSearchList(getTabTagInterface?.getTabTagText().toString())
        }

        mSearchHistoryAdapter = SearchHistoryAdapter(this)
        fv_history_search.adapter = mSearchHistoryAdapter
        mSearchHistoryAdapter.setItemClickCallback { position, item, view ->
            goToSearchList(item.key)
        }

        tv_clear_all_search_history.setOnClickListener {
            mPresenter?.clearAllHistory()
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
        searchView.editText {
            setOnEditorActionListener(mKeyListener)
        }

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
            mSearchKey = query.toString()
            goToSearchList(mSearchKey)
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return false
        }
    }

    private val mKeyListener = OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) //键盘上点击搜索按钮
            {
                goToSearchList(mSearchKey)
                FKeyboardUtil.hideKeyboard(v)
                return@OnEditorActionListener true
            }
            false
        }

    private fun goToSearchList(key: String) {
        mPresenter?.saveSearchKey(key)
        startActivity<SearchContentActivity>(Pair(Constant.EXTRA_SEARCH_KEY, key))
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
        listHistoryBean.let {
            mSearchHistoryAdapter.run {
                dataHolder.data = listHistoryBean
            }
            if (FCollectionUtil.isEmpty(listHistoryBean)) {
                rl_clear_all_search_history.visibility = View.GONE
            } else {
                rl_clear_all_search_history.visibility = View.VISIBLE
            }
        }
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