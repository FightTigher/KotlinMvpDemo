package com.ecovacs.kotlinmvpdemo.view.recyclerview.adapter

/**
 * Created by liang.liu on 2018/1/28.
 */
interface OnItemLongClickListener {
    fun onItemLongClick(obj: Any?, position: Int): Boolean
}