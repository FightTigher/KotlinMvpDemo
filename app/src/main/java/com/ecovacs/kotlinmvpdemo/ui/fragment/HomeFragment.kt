package com.ecovacs.kotlinmvpdemo.ui.fragment

import android.os.Bundle
import com.ecovacs.kotlinmvpdemo.R
import com.ecovacs.kotlinmvpdemo.base.BaseFragment
import com.ecovacs.kotlinmvpdemo.mvp.model.bean.HomeBean
import com.ecovacs.kotlinmvpdemo.mvp.model.contract.HomeContract

/**
 * Created by liang.liu on 2018/1/23.
 */
class HomeFragment : BaseFragment(), HomeContract.View {

    private var mTitle: String? = null

    companion object {
        fun getInstance(title: String): HomeFragment {
            val fragment = HomeFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.select_dialog_item_material
    }

    override fun lazyLoad() {

    }

    override fun initView() {

    }

    override fun showLoading() {
      
    }

    override fun dismissLoading() {
      
    }

    override fun setHomeData(homeBean: HomeBean) {
      
    }

    override fun setMoreData(itemList: ArrayList<HomeBean.Issue.Item>) {
      
    }

    override fun showError(msg: String, errorCode: Int) {
      
    }
}