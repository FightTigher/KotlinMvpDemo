package com.ecovacs.kotlinmvpdemo.mvp.contract

import com.ecovacs.kotlinmvpdemo.base.IBaseView
import com.ecovacs.kotlinmvpdemo.base.IPresenter
import com.ecovacs.kotlinmvpdemo.mvp.model.bean.HomeBean

/**
 * Created by liang.liu on 2018/1/31.
 */
interface VideoDetailContract {

    interface View : IBaseView {

        fun setVideo(url: String)

        fun setVideoInfo(itemInfo: HomeBean.Issue.Item)

        fun setBackground(url: String)

        fun setRecentRelatedVideo(itemList: ArrayList<HomeBean.Issue.Item>)

        fun setErrorMsg(errorMsg: String)

    }

    interface Presenter : IPresenter<View> {


        /**
         * 加载视频信息
         */
        fun loadVideoInfo(itemInfo: HomeBean.Issue.Item)


        /**
         * 请求相关的视频数据
         */
        fun requestRelatedVideo(id: Long)
    }
}