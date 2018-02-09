package com.ecovacs.kotlinmvpdemo.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.ecovacs.kotlinmvpdemo.R
import com.ecovacs.kotlinmvpdemo.base.BaseFragment
import com.ecovacs.kotlinmvpdemo.base.BaseFragmentAdapter
import com.ecovacs.kotlinmvpdemo.mvp.contract.HotContract
import com.ecovacs.kotlinmvpdemo.mvp.model.bean.TabInfoBean
import com.ecovacs.kotlinmvpdemo.mvp.presenter.HotPresenter
import com.ecovacs.kotlinmvpdemo.net.exception.ErrorStatus
import com.ecovacs.kotlinmvpdemo.showToast
import com.ecovacs.kotlinmvpdemo.utils.StatusBarUtil
import kotlinx.android.synthetic.main.fragment_hot.*


/**
 * Created by liang.liu on 2018/1/23.
 */
class HotFragment : BaseFragment() ,HotContract.View{


    private var mTitle: String? = null

    private val mPresenter by lazy { HotPresenter() }

    private val mTabTitleList = ArrayList<String>()

    private val mFragmentList = ArrayList<Fragment>()

    companion object {
        fun getInstance(title: String): HotFragment {
            val fragment = HotFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_hot

    init {
        mPresenter.attachView(this)
    }

    override fun lazyLoad() {
        mPresenter.getTabInfo()
    }

    override fun initView() {
        mLayoutStatusView = multipleStatusView
        StatusBarUtil.darkMode(activity)
        StatusBarUtil.setPaddingSmart(activity, toolbar)
    }

    override fun showLoading() {
        mLayoutStatusView?.showLoading()
    }

    override fun dismissLoading() {
        mLayoutStatusView?.showContent()
    }

    override fun setTabInfo(tabInfoBean: TabInfoBean) {

        tabInfoBean.tabInfo.tabList.mapTo(mTabTitleList) { it.name }
        tabInfoBean.tabInfo.tabList.mapTo(mFragmentList) { RankFragment.getInstance(it.apiUrl) }

        mViewPager.adapter = BaseFragmentAdapter(childFragmentManager, mFragmentList, mTabTitleList)
        mTabLayout.setupWithViewPager(mViewPager)
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        showToast(errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}