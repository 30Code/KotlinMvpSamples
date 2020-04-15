package cn.linhome.kotlinmvpsamples.constant

import cn.linhome.kotlinmvpsamples.BuildConfig

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/5
 */
object Constant {

    val DEBUG = BuildConfig.DEBUG

    /**
     * webview 要加载的链接
     */
    const val EXTRA_URL = "extra_url"

    /**
     * id key
     */
    const val CONTENT_CID_KEY = "cid"

    const val BUGLY_ID = "76e2b2867d"

    const val BASE_URL = "https://www.wanandroid.com/"

    const val LOGIN_KEY = "login"
    const val USERNAME_KEY = "username"
    const val PASSWORD_KEY = "password"
    const val TOKEN_KEY = "token"
    const val HAS_NETWORK_KEY = "has_network"

    const val TODO_NO = "todo_no"
    const val TODO_ADD = "todo_add"
    const val TODO_DONE = "todo_done"

}