package com.ecovacs.kotlinmvpdemo.mvp.model

import com.ecovacs.kotlinmvpdemo.mvp.model.bean.HomeBean
import com.ecovacs.kotlinmvpdemo.net.RetrofitManager
import com.ecovacs.kotlinmvpdemo.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * Created by liang.liu on 2018/2/9.
 */
class RankModel {

    fun requestRankList(apiUrl: String): Observable<HomeBean.Issue> {

        return RetrofitManager.service.getIssueData(apiUrl)
                .compose(SchedulerUtils.ioToMain())
    }

}