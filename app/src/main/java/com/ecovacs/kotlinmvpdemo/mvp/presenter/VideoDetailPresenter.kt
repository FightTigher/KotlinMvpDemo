package com.ecovacs.kotlinmvpdemo.mvp.presenter

import android.app.Activity
import com.ecovacs.kotlinmvpdemo.MyApplication
import com.ecovacs.kotlinmvpdemo.base.BasePresenter
import com.ecovacs.kotlinmvpdemo.dataFormat
import com.ecovacs.kotlinmvpdemo.mvp.contract.VideoDetailContract
import com.ecovacs.kotlinmvpdemo.mvp.model.VideoDetailModel
import com.ecovacs.kotlinmvpdemo.mvp.model.bean.HomeBean
import com.ecovacs.kotlinmvpdemo.net.exception.ExceptionHandle
import com.ecovacs.kotlinmvpdemo.showToast
import com.ecovacs.kotlinmvpdemo.utils.DisplayManager
import com.ecovacs.kotlinmvpdemo.utils.NetworkUtil

/**
 * Created by liang.liu on 2018/1/31.
 */
class VideoDetailPresenter : BasePresenter<VideoDetailContract.View>(), VideoDetailContract.Presenter {


    private val videoDetailModel: VideoDetailModel  by lazy {
        VideoDetailModel()
    }

    override fun loadVideoInfo(itemInfo: HomeBean.Issue.Item) {
        val playInfo = itemInfo.data?.playInfo

        val netType = NetworkUtil.isWifi(MyApplication.context)

        checkViewAttached()

        if (playInfo!!.size > 1) {
            if (netType) {
                for (i in playInfo) {
                    if (i.type == "high") {
                        val playUrl = i.url
                        mRootView?.setVideo(playUrl)
                    }
                }
            } else {
                for (i in playInfo) {
                    if (i.type == "normal") {
                        val playUrl = i.url
                        mRootView?.setVideo(playUrl)

                        (mRootView as Activity).showToast("本次消耗${(mRootView as Activity)
                                .dataFormat(i.urlList[0].size)}流量")
                    }
                }
            }
        } else {
            mRootView?.setVideo(itemInfo.data.playUrl)
        }


        //设置背景
        val backgroundUrl = itemInfo.data.cover.blurred + "/thumbnail/${DisplayManager.getScreenHeight()!! - DisplayManager.dip2px(250f)!!}x${DisplayManager.getScreenWidth()}"
        backgroundUrl.let { mRootView?.setBackground(it) }

        mRootView?.setVideoInfo(itemInfo)


    }

    override fun requestRelatedVideo(id: Long) {
        mRootView?.showLoading()
        val disposable = videoDetailModel.requestRelatedData(id)
                .subscribe({ issue ->
                    mRootView?.apply {
                        dismissLoading()
                        setRecentRelatedVideo(issue.itemList)
                    }
                }, { t ->
                    mRootView?.apply {
                        dismissLoading()
                        setErrorMsg(ExceptionHandle.handleException(t))
                    }

                })

        addSubscription(disposable)
    }
}