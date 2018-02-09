package com.ecovacs.kotlinmvpdemo.mvp.presenter

import com.ecovacs.kotlinmvpdemo.base.BasePresenter
import com.ecovacs.kotlinmvpdemo.mvp.contract.HotContract
import com.ecovacs.kotlinmvpdemo.mvp.model.HotModel
import com.ecovacs.kotlinmvpdemo.net.exception.ExceptionHandle

/**
 * Created by liang.liu on 2018/2/9.
 */
class HotPresenter : BasePresenter<HotContract.View>(), HotContract.Presenter {

    private val model by lazy { HotModel() }

    override fun getTabInfo() {

        checkViewAttached()

        mRootView?.showLoading()
        val disposable = model.getTabInfo()
                .subscribe({ it ->
                    mRootView?.apply {
                        dismissLoading()
                        setTabInfo(it)
                    }
                }, { t ->
                    mRootView?.apply {
                        dismissLoading()
                        showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                    }
                })

        addSubscription(disposable)

    }

}