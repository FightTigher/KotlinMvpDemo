package com.ecovacs.kotlinmvpdemo.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.ecovacs.kotlinmvpdemo.R
import com.ecovacs.kotlinmvpdemo.base.BaseFragment
import com.ecovacs.kotlinmvpdemo.mvp.contract.FollowContract
import com.ecovacs.kotlinmvpdemo.mvp.model.bean.HomeBean
import com.ecovacs.kotlinmvpdemo.mvp.presenter.FollowPresenter
import com.ecovacs.kotlinmvpdemo.net.exception.ErrorStatus
import com.ecovacs.kotlinmvpdemo.showToast
import com.ecovacs.kotlinmvpdemo.ui.adapter.FollowAdapter
import kotlinx.android.synthetic.main.layout_recyclerview.*

/**
 * Created by liang.liu on 2018/2/1.
 */
class FollowFragment : BaseFragment(), FollowContract.View {


    private lateinit var mTitle: String

    private var loadingMore: Boolean = false

    private var itemList: ArrayList<HomeBean.Issue.Item> = ArrayList()

    companion object {
        fun getInstance(title: String): FollowFragment {
            val fragment = FollowFragment()
            var bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    private val mPresenter: FollowPresenter by lazy {
        FollowPresenter()
    }

    private val mFollowAdapter: FollowAdapter by lazy {
        FollowAdapter(activity, itemList)
    }


    override fun getLayoutId(): Int = R.layout.layout_recyclerview

    override fun initView() {
        mPresenter.attachView(this)

        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = mFollowAdapter

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val itemCount = mRecyclerView.layoutManager.itemCount

                val lastVisibileItem = (mRecyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                if (lastVisibileItem == (itemCount - 1) && !loadingMore) {
                    loadingMore = true
                    mPresenter.loadMore()
                }
            }
        })
    }

    override fun lazyLoad() {
        mPresenter.requestFollowList()
    }


    override fun setFollowInfo(issue: HomeBean.Issue) {
        loadingMore = false
        itemList = issue.itemList
        mFollowAdapter.addData(itemList)
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        showToast(errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            multipleStatusView.showNoNetwork()
        } else {
            multipleStatusView.showError()
        }
    }

    override fun showLoading() {
        multipleStatusView.showLoading()
    }

    override fun dismissLoading() {
        multipleStatusView.showContent()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}