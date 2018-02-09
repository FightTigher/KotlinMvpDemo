package com.ecovacs.kotlinmvpdemo.mvp.model

import com.ecovacs.kotlinmvpdemo.mvp.model.bean.TabInfoBean
import com.ecovacs.kotlinmvpdemo.net.RetrofitManager
import com.ecovacs.kotlinmvpdemo.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * Created by liang.liu on 2018/2/9.
 */
class HotModel {


    fun getTabInfo() : Observable<TabInfoBean>{

        return RetrofitManager.service.getRankList()
                .compose(SchedulerUtils.ioToMain())

    }
}