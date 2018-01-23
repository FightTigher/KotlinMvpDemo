package com.ecovacs.kotlinmvpdemo.base

/**
 * Created by liang.liu on 2018/1/23.
 */
interface IPresenter<in V : IBaseView> {

    fun attachView(mRootView: V)

    fun detachView()
}