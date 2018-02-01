package com.ecovacs.kotlinmvpdemo.mvp.model

import com.ecovacs.kotlinmvpdemo.mvp.model.bean.CategoryBean
import com.ecovacs.kotlinmvpdemo.net.RetrofitManager
import com.ecovacs.kotlinmvpdemo.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * Created by liang.liu on 2018/2/1.
 */
class CategoryModel {

    fun getCategoryData(): Observable<ArrayList<CategoryBean>> {
        return RetrofitManager.service.getCategory()
                .compose(SchedulerUtils.ioToMain())
    }

}