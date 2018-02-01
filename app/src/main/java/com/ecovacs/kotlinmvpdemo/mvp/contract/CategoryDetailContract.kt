package com.ecovacs.kotlinmvpdemo.mvp.contract

import com.ecovacs.kotlinmvpdemo.base.IBaseView
import com.ecovacs.kotlinmvpdemo.base.IPresenter
import com.ecovacs.kotlinmvpdemo.mvp.model.bean.HomeBean

/**
 * Created by liang.liu on 2018/2/1.
 */
interface CategoryDetailContract {

    interface View : IBaseView {
        fun setCateDetailList(itemList:ArrayList<HomeBean.Issue.Item>)

        fun showError(errorMsg:String)
    }

    interface Presenter : IPresenter<View> {
        fun getCategoryDetailList(id:Long)

        fun loadMoreData()
    }
}