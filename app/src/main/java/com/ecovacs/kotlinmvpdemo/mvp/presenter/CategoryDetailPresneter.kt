package com.ecovacs.kotlinmvpdemo.mvp.presenter

import com.ecovacs.kotlinmvpdemo.base.BasePresenter
import com.ecovacs.kotlinmvpdemo.mvp.contract.CategoryDetailContract
import com.ecovacs.kotlinmvpdemo.mvp.model.CategoryDetailModel

/**
 * Created by liang.liu on 2018/2/1.
 */
class CategoryDetailPresneter : BasePresenter<CategoryDetailContract.View>(), CategoryDetailContract.Presenter {

    private val model by lazy { CategoryDetailModel() }

    private var nextPageUrl: String? = null

    override fun getCategoryDetailList(id: Long) {

        checkViewAttached()

        val disposable = model.getCategoryDetailList(id)
                .subscribe({ it ->
                    mRootView?.apply {
                        nextPageUrl = it.nextPageUrl
                        setCateDetailList(it.itemList)
                    }
                }, { t ->
                    mRootView?.apply {
                        showError(t.toString())
                    }
                })

        addSubscription(disposable)
    }

    override fun loadMoreData() {

        val disposable = nextPageUrl?.let {
            model.loadMoreData(it)
                    .subscribe({ issue ->
                        mRootView?.apply {
                            nextPageUrl = issue.nextPageUrl
                            setCateDetailList(issue.itemList)
                        }
                    }, { throwable ->
                        mRootView?.apply {
                            showError(throwable.toString())
                        }
                    })
        }

        disposable?.let { addSubscription(it) }
    }

}