package cn.linhome.kotlinmvpsamples.ui.activity

import android.text.TextUtils
import android.webkit.WebChromeClient
import android.webkit.WebView
import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.base.BaseActivity
import cn.linhome.lib.jshandler.JsHandler
import cn.linhome.lib.webview.FWebView
import kotlinx.android.synthetic.main.act_webview.*

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/11
 */
class WanWebViewActivity : BaseActivity(){

    /**
     * webview 要加载的链接
     */
    val EXTRA_URL = "extra_url"

    private lateinit var mUrl : String

    override fun onCreateContentView(): Int = R.layout.act_webview

    override fun initView() {

        mUrl = intent.getStringExtra(EXTRA_URL)

        showTitle(true)
        val jsHander = JsHandler(this)
        wv_article.addJavascriptInterface(jsHander, jsHander.name)
        wv_article.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView, title: String) {
                getTitleView().itemMiddle.setTextTop(view.title)
            }
        }
        if (!TextUtils.isEmpty(mUrl)) {
            wv_article!![mUrl]
        }
    }

    override fun start() {

    }
}