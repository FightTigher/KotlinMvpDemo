package com.ecovacs.kotlinmvpdemo.mvp.presenter

import com.ecovacs.kotlinmvpdemo.base.BasePresenter
import com.ecovacs.kotlinmvpdemo.mvp.contract.FollowContract
import com.ecovacs.kotlinmvpdemo.mvp.model.FollowModel
import com.ecovacs.kotlinmvpdemo.net.exception.ExceptionHandle

/**
 * Created by liang.liu on 2018/2/1.
 */
class FollowPresenter : BasePresenter<FollowContract.View>(), FollowContract.Presetner {

    private val model by lazy {
        FollowModel()
    }

    private var nextPageUrl:String?=null

    override fun requestFollowList() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestFollowList()
                .subscribe({ it ->
                    mRootView?.apply {
                        dismissLoading()
                        nextPageUrl = it.nextPageUrl
                        setFollowInfo(it)
                    }
                }, { t ->
                    mRootView?.apply {
                        //处理异常
                        showError(ExceptionHandle.handleException(t),ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable)
    }

    override fun loadMore() {
        val disposable = nextPageUrl?.let {
            model.loadMore(it)
                    .subscribe({ issue->
                        mRootView?.apply {
                            nextPageUrl = issue.nextPageUrl
                            setFollowInfo(issue)
                        }

                    },{ t ->
                        mRootView?.apply {
                            showError(ExceptionHandle.handleException(t),ExceptionHandle.errorCode)
                        }
                    })


        }
        if (disposable != null) {
            addSubscription(disposable)
        }
    }


}