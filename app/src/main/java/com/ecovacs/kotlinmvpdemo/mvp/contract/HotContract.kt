package com.ecovacs.kotlinmvpdemo.mvp.contract

import com.ecovacs.kotlinmvpdemo.base.IBaseView
import com.ecovacs.kotlinmvpdemo.base.IPresenter
import com.ecovacs.kotlinmvpdemo.mvp.model.bean.TabInfoBean

/**
 * Created by liang.liu on 2018/2/9.
 */
interface HotContract {

    interface View : IBaseView {
        /**
         * 设置 TabInfo
         */
        fun setTabInfo(tabInfoBean: TabInfoBean)

        fun showError(errorMsg:String,errorCode:Int)
    }

    interface Presenter : IPresenter<View> {
        fun getTabInfo()

    }
}