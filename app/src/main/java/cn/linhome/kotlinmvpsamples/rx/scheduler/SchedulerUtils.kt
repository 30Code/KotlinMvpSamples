package cn.linhome.kotlinmvpsamples.rx.scheduler

/**
 *  des :
 *  Created by 30Code
 *  date : 2020/4/5
 */
object SchedulerUtils {

    fun <T> ioToMain() : IoMainScheduler<T> {
        return IoMainScheduler()
    }

}