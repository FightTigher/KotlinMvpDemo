package com.ecovacs.kotlinmvpdemo.ui.fragment

import android.os.Bundle
import com.ecovacs.kotlinmvpdemo.base.BaseFragment

/**
 * Created by liang.liu on 2018/1/23.
 */
class HotFragment : BaseFragment(){

    private var mTitle: String? = null

    companion object {
        fun getInstance(title: String): HotFragment {
            val fragment = HotFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun lazyLoad() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}