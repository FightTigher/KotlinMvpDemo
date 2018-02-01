package com.ecovacs.kotlinmvpdemo.mvp.presenter

import com.ecovacs.kotlinmvpdemo.base.BasePresenter
import com.ecovacs.kotlinmvpdemo.mvp.contract.CategoryDetailContract

/**
 * Created by liang.liu on 2018/2/1.
 */
class CategoryDetailPresneter : BasePresenter<CategoryDetailContract.View>(), CategoryDetailContract.Presenter {
    override fun getCategoryDetailList(id: Long) {

    }

    override fun loadMoreData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}