package com.ecovacs.kotlinmvpdemo.mvp.contract

import com.ecovacs.kotlinmvpdemo.base.IBaseView
import com.ecovacs.kotlinmvpdemo.base.IPresenter
import com.ecovacs.kotlinmvpdemo.mvp.model.bean.HomeBean

/**
 * Created by liang.liu on 2018/2/1.
 */
interface FollowContract {

    interface View : IBaseView {

        fun setFollowInfo(issue: HomeBean.Issue)

        fun showError(errorMsg: String, errorCode: Int)
    }

    interface Presetner : IPresenter<View> {

        fun requestFollowList()

        fun loadMore()
    }
}