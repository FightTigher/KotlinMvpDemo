package com.ecovacs.kotlinmvpdemo.mvp.model

import com.ecovacs.kotlinmvpdemo.mvp.model.bean.HomeBean
import com.ecovacs.kotlinmvpdemo.net.RetrofitManager
import com.ecovacs.kotlinmvpdemo.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * Created by liang.liu on 2018/2/8.
 */
class CategoryDetailModel {

    fun getCategoryDetailList(id: Long): Observable<HomeBean.Issue> {

        return RetrofitManager.service.getCategoryDetailList(id)
                .compose(SchedulerUtils.ioToMain())
    }

    fun loadMoreData(url: String): Observable<HomeBean.Issue> {
        return RetrofitManager.service.getIssueData(url)
                .compose(SchedulerUtils.ioToMain())
    }
}