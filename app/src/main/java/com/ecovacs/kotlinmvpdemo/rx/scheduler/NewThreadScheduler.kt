package com.ecovacs.kotlinmvpdemo.rx.scheduler

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by liang.liu on 2018/1/30.
 */
class NewThreadScheduler<T> private constructor(): BaseScheduler<T>(Schedulers.newThread(), AndroidSchedulers.mainThread())