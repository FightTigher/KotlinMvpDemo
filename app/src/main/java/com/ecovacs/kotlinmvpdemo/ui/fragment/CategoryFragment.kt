package com.ecovacs.kotlinmvpdemo.ui.fragment

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ecovacs.kotlinmvpdemo.R
import com.ecovacs.kotlinmvpdemo.base.BaseFragment
import com.ecovacs.kotlinmvpdemo.mvp.contract.CategoryContract
import com.ecovacs.kotlinmvpdemo.mvp.model.bean.CategoryBean
import com.ecovacs.kotlinmvpdemo.mvp.presenter.CategoryPresenter
import com.ecovacs.kotlinmvpdemo.net.exception.ErrorStatus
import com.ecovacs.kotlinmvpdemo.showToast
import com.ecovacs.kotlinmvpdemo.ui.adapter.CategoryAdapter
import com.ecovacs.kotlinmvpdemo.utils.DisplayManager
import kotlinx.android.synthetic.main.layout_recyclerview.*

/**
 * Created by liang.liu on 2018/2/1.
 */
class CategoryFragment : BaseFragment(), CategoryContract.View {
    private var mCategoryList = ArrayList<CategoryBean>()

    private var mTitle: String? = null

    private val mAdapter by lazy { CategoryAdapter(activity, mCategoryList, R.layout.item_category) }

    companion object {
        fun getInstance(title: String): CategoryFragment {
            val fragment = CategoryFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    private val mPresenter: CategoryPresenter by lazy {
        CategoryPresenter()
    }

    override fun getLayoutId(): Int = R.layout.fragment_category

    override fun initView() {
        mPresenter.attachView(this)

        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        mRecyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                val position = parent.getChildPosition(view)
                val offset = DisplayManager.dip2px(2f)!!

                outRect.set(if (position % 2 == 0) 0 else offset, offset,
                        if (position % 2 == 0) offset else 0, offset)
            }
        })

    }

    override fun lazyLoad() {
        mPresenter.getCategoryData()
    }

    override fun showCategory(categoryList: ArrayList<CategoryBean>) {
        mCategoryList = categoryList
        mAdapter.setData(mCategoryList)
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        showToast(errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR)
            multipleStatusView.showNoNetwork()
        else
            multipleStatusView.showError()
    }

    override fun showLoading() {
        multipleStatusView.showLoading()
    }

    override fun dismissLoading() {
        multipleStatusView.showContent()
    }
}