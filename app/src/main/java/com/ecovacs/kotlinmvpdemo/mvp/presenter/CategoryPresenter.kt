package com.ecovacs.kotlinmvpdemo.mvp.presenter

import com.ecovacs.kotlinmvpdemo.base.BasePresenter
import com.ecovacs.kotlinmvpdemo.mvp.contract.CategoryContract
import com.ecovacs.kotlinmvpdemo.mvp.model.CategoryModel
import com.ecovacs.kotlinmvpdemo.net.exception.ExceptionHandle

/**
 * Created by liang.liu on 2018/2/1.
 */
class CategoryPresenter : BasePresenter<CategoryContract.View>(), CategoryContract.Presenter {

    private val model by lazy {
        CategoryModel()
    }

    override fun getCategoryData() {
        checkViewAttached()

        mRootView?.showLoading()
        val disposable = model.getCategoryData()
                .subscribe({ it ->
                    mRootView?.apply {
                        dismissLoading()
                        showCategory(it)
                    }
                }, { t ->
                    mRootView?.apply {
                        //处理异常
                        showError(ExceptionHandle.handleException(t),ExceptionHandle.errorCode)
                    }

                })

        addSubscription(disposable)
    }


}