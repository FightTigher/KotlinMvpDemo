package com.ecovacs.kotlinmvpdemo.mvp.presenter

import com.ecovacs.kotlinmvpdemo.base.BasePresenter
import com.ecovacs.kotlinmvpdemo.mvp.contract.RankContract
import com.ecovacs.kotlinmvpdemo.mvp.model.RankModel
import com.ecovacs.kotlinmvpdemo.net.exception.ExceptionHandle

/**
 * Created by liang.liu on 2018/2/9.
 */
class RankPresenter : BasePresenter<RankContract.View>(), RankContract.Presenter {

    private val model by lazy { RankModel() }

    override fun requestRankList(apiUrl: String) {
        checkViewAttached()
        mRootView?.showLoading()

        val disposable = model.requestRankList(apiUrl)
                .subscribe({ it ->
                    mRootView?.apply {

                        dismissLoading()
                        setRankList(it.itemList)

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