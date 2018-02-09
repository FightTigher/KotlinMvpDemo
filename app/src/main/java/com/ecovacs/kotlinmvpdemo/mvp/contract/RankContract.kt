package com.ecovacs.kotlinmvpdemo.mvp.contract

import com.ecovacs.kotlinmvpdemo.base.IBaseView
import com.ecovacs.kotlinmvpdemo.base.IPresenter
import com.ecovacs.kotlinmvpdemo.mvp.model.bean.HomeBean

/**
 * Created by liang.liu on 2018/2/9.
 */
interface RankContract {

    interface View : IBaseView {
        /**
         * 设置排行榜的数据
         */
        fun setRankList(itemList: ArrayList<HomeBean.Issue.Item>)

        fun showError(errorMsg:String,errorCode:Int)
    }

    interface Presenter : IPresenter<View> {
        /**
         * 获取 TabInfo
         */
        fun requestRankList(apiUrl:String)
    }
}