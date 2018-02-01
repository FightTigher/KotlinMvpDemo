package com.ecovacs.kotlinmvpdemo.mvp.model

import com.ecovacs.kotlinmvpdemo.mvp.model.bean.HomeBean
import com.ecovacs.kotlinmvpdemo.net.RetrofitManager
import com.ecovacs.kotlinmvpdemo.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * Created by liang.liu on 2018/1/31.
 */
class VideoDetailModel {

    fun requestRelatedData(id: Long): Observable<HomeBean.Issue> {

        return RetrofitManager.service.getRelatedData(id)
                .compose(SchedulerUtils.ioToMain())

    }
}