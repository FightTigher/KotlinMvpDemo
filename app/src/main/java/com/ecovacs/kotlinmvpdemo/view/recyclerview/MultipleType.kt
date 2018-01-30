package com.ecovacs.kotlinmvpdemo.view.recyclerview

/**
 * Created by liang.liu on 2018/1/28.
 */
interface MultipleType<in T> {
    fun getLayoutId(item: T, position: Int): Int
}