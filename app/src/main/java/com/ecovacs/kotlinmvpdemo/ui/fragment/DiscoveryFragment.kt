package com.ecovacs.kotlinmvpdemo.ui.fragment

import android.os.Bundle
import com.ecovacs.kotlinmvpdemo.R
import com.ecovacs.kotlinmvpdemo.base.BaseFragment
import com.ecovacs.kotlinmvpdemo.utils.StatusBarUtil
import kotlinx.android.synthetic.main.fragment_hot.*

/**
 * Created by liang.liu on 2018/1/23.
 */
class DiscoveryFragment : BaseFragment() {

    private var mTitle: String? = null

    companion object {
        fun getInstance(title: String): DiscoveryFragment {
            val fragment = DiscoveryFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_hot

    override fun lazyLoad() {

    }

    override fun initView() {

        //状态栏透明和间距处理
        StatusBarUtil.darkMode(activity)
        StatusBarUtil.setPaddingSmart(activity, toolbar)
    }
}