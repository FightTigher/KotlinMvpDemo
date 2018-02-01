package com.ecovacs.kotlinmvpdemo.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.ecovacs.kotlinmvpdemo.R
import com.ecovacs.kotlinmvpdemo.base.BaseFragment
import com.ecovacs.kotlinmvpdemo.base.BaseFragmentAdapter
import com.ecovacs.kotlinmvpdemo.utils.StatusBarUtil
import kotlinx.android.synthetic.main.fragment_hot.*

/**
 * Created by liang.liu on 2018/1/23.
 */
class DiscoveryFragment : BaseFragment() {

    private var mTitle: String? = null

    private val tabList = ArrayList<String>()

    private val fragments = ArrayList<Fragment>()

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


        tv_header_title.text = mTitle

        tabList.add("关注")
        tabList.add("分类")

        fragments.add(FollowFragment.getInstance("关注"))
        fragments.add(CategoryFragment.getInstance("分类"))

        mViewPager.adapter = BaseFragmentAdapter(childFragmentManager,fragments,tabList)
        mTabLayout.setupWithViewPager(mViewPager)
//        TabLayoutHelper
    }
}