package com.ecovacs.kotlinmvpdemo.rx.scheduler

/**
 * Created by liang.liu on 2018/1/30.
 */
object SchedulerUtils {

    fun <T> ioToMain(): IoMainScheduler<T> {
        return IoMainScheduler()
    }

}