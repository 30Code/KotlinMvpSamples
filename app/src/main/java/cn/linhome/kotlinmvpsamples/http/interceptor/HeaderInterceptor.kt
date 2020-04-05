package cn.linhome.kotlinmvpsamples.http.interceptor

import cn.linhome.kotlinmvpsamples.constant.Constant
import cn.linhome.kotlinmvpsamples.constant.HttpConstant
import cn.linhome.lib.utils.context.FPreferencesUtil
import okhttp3.Interceptor
import okhttp3.Response

/**
 *  des : HeaderInterceptor: 设置请求头
 *  Created by 30Code
 *  date : 2020/4/5
 */
class HeaderInterceptor : Interceptor{

    /**
     * token
     */
    private var token: String = FPreferencesUtil.getString(Constant.TOKEN_KEY, "")

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val builder = request.newBuilder()

        builder.addHeader("Content-type", "application/json; charset=utf-8")
        // .header("token", token)
        // .method(request.method(), request.body())

        val domain = request.url().host()
        val url = request.url().toString()
        if (domain.isNotEmpty() && (url.contains(HttpConstant.COLLECTIONS_WEBSITE)
                    || url.contains(HttpConstant.UNCOLLECTIONS_WEBSITE)
                    || url.contains(HttpConstant.ARTICLE_WEBSITE)
                    || url.contains(HttpConstant.TODO_WEBSITE)
                    || url.contains(HttpConstant.COIN_WEBSITE))) {
            val spDomain: String = FPreferencesUtil.getString(domain, "")
            val cookie: String = if (spDomain.isNotEmpty()) spDomain else ""
            if (cookie.isNotEmpty()) {
                // 将 Cookie 添加到请求头
                builder.addHeader(HttpConstant.COOKIE_NAME, cookie)
            }
        }

        return chain.proceed(builder.build())
    }

}