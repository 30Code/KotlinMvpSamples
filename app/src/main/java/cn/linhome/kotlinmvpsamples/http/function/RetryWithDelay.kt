package cn.linhome.kotlinmvpsamples.http.function

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 *  des : 请求重连
 *  Created by 30Code
 *  date : 2020/4/5
 */
class RetryWithDelay : Function<Observable<out Throwable>, Observable<*>>{

    private var mMaxRetryCount = 3 // 可重试次数
    private var mRetryDelayMillis : Long = 3000 // 重试等待时间

    constructor() {}

    constructor(mRetryDelayMillis : Long) {
        this.mRetryDelayMillis = mRetryDelayMillis
    }

    constructor(mMaxRetryCount : Int, mRetryDelayMillis : Long) {
        this.mMaxRetryCount = mMaxRetryCount
        this.mRetryDelayMillis = mRetryDelayMillis
    }

    @Throws(Exception::class)
    override fun apply(observable : Observable<out Throwable>): Observable<*> {
        return observable
            .zipWith(Observable.range(1, mMaxRetryCount + 1),
                BiFunction<Throwable, Int, Wrapper> { t1, t2 -> Wrapper(t2, t1) })
            .flatMap { wrapper ->
                val t = wrapper.throwable
                if ((t is ConnectException
                            || t is SocketTimeoutException
                            || t is TimeoutException
                            || t is HttpException)
                    && wrapper.index < mMaxRetryCount + 1) {
                    Observable.timer(mRetryDelayMillis * wrapper.index, TimeUnit.MILLISECONDS)
                } else Observable.error<Any>(wrapper.throwable)
            }
    }

    private inner class Wrapper(val index: Int, val throwable: Throwable)


}