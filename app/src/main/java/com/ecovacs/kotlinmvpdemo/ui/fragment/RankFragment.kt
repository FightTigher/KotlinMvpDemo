package com.ecovacs.kotlinmvpdemo.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ecovacs.kotlinmvpdemo.R
import com.ecovacs.kotlinmvpdemo.base.BaseFragment
import com.ecovacs.kotlinmvpdemo.mvp.contract.RankContract
import com.ecovacs.kotlinmvpdemo.mvp.model.bean.HomeBean
import com.ecovacs.kotlinmvpdemo.mvp.presenter.RankPresenter
import com.ecovacs.kotlinmvpdemo.net.exception.ErrorStatus
import com.ecovacs.kotlinmvpdemo.showToast
import com.ecovacs.kotlinmvpdemo.ui.adapter.CategoryDetailAdapter
import kotlinx.android.synthetic.main.layout_recyclerview.*

/**
 * Created by liang.liu on 2018/2/9.
 */
class RankFragment : BaseFragment(), RankContract.View {


    private var apiUrl: String? = null

    private val mPresenter by lazy { RankPresenter() }

    private var itemList = ArrayList<HomeBean.Issue.Item>()

    private val mAdapter by lazy { CategoryDetailAdapter(activity, itemList, R.layout.item_category_detail) }

    companion object {

        fun getInstance(apiUrl: String): RankFragment {
            val fragment = RankFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.apiUrl = apiUrl
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_rank

    init {
        mPresenter.attachView(this)
    }

    override fun initView() {
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = mAdapter

        mLayoutStatusView = multipleStatusView
    }

    override fun lazyLoad() {
        if (!apiUrl.isNullOrEmpty()) {
            mPresenter.requestRankList(apiUrl!!)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun showLoading() {
        multipleStatusView.showLoading()
    }

    override fun dismissLoading() {
        multipleStatusView.showContent()
    }

    override fun setRankList(itemList: ArrayList<HomeBean.Issue.Item>) {
        mAdapter.addData(itemList)
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        showToast(errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            multipleStatusView.showNoNetwork()
        } else {
            multipleStatusView.showError()
        }
    }
}