package cn.linhome.kotlinmvpsamples.ui.activity

import android.os.Build
import android.text.TextUtils
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import cn.linhome.kotlinmvpsamples.R
import cn.linhome.kotlinmvpsamples.base.BaseActivity
import cn.linhome.kotlinmvpsamples.constant.Constant
import cn.linhome.lib.jshandler.JsHandler
import cn.linhome.lib.title.FTitleItem
import kotlinx.android.synthetic.main.act_webview.*

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/11
 */
class WanWebViewActivity : BaseActivity(){

    private lateinit var mUrl : String

    override fun onCreateContentView(): Int = R.layout.act_webview

    override fun initView() {

        mUrl = intent.getStringExtra(Constant.EXTRA_URL)

        showTitle(true)
        getTitleView().itemMiddle.setTextTop(getString(R.string.res_loading))

        val jsHander = JsHandler(this)
        wv_article.addJavascriptInterface(jsHander, jsHander.name)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            wv_article.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
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

    override fun onClickItemLeftTitleBar(index: Int, item: FTitleItem?) {
        super.onClickItemLeftTitleBar(index, item)
        finish()
    }

    override fun onBackPressed() {
        if (wv_article.canGoBack()) {
            wv_article.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        wv_article.destroy()
        super.onDestroy()
    }
}