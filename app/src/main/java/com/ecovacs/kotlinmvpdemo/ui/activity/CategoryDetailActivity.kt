package com.ecovacs.kotlinmvpdemo.ui.activity

import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.ecovacs.kotlinmvpdemo.Constants
import com.ecovacs.kotlinmvpdemo.R
import com.ecovacs.kotlinmvpdemo.base.BaseActivity
import com.ecovacs.kotlinmvpdemo.glide.GlideApp
import com.ecovacs.kotlinmvpdemo.mvp.contract.CategoryDetailContract
import com.ecovacs.kotlinmvpdemo.mvp.model.bean.CategoryBean
import com.ecovacs.kotlinmvpdemo.mvp.model.bean.HomeBean
import com.ecovacs.kotlinmvpdemo.mvp.presenter.CategoryDetailPresneter
import com.ecovacs.kotlinmvpdemo.ui.adapter.CategoryDetailAdapter
import com.ecovacs.kotlinmvpdemo.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_category_detail.*

/**
 * Created by liang.liu on 2018/2/1.
 */
class CategoryDetailActivity : BaseActivity(), CategoryDetailContract.View {

    private val mAdapter by lazy { CategoryDetailAdapter(this, itemList, R.layout.item_category_detail) }

    private val mPresenter by lazy { CategoryDetailPresneter() }

    override fun layoutId(): Int = R.layout.activity_category_detail

    private var itemList = ArrayList<HomeBean.Issue.Item>()

    init {
        mPresenter.attachView(this)
    }

    private var loadingMore = false

    private var categoryData: CategoryBean? = null

    override fun initData() {
        categoryData = intent.getSerializableExtra(Constants.BUNDLE_CATEGORY_DATA) as CategoryBean?
    }

    override fun initView() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        // 加载headerImage
        GlideApp.with(this)
                .load(categoryData?.headerImage)
                .placeholder(R.color.color_darker_gray)
                .into(imageView)

        tv_category_desc.text = "#${categoryData?.description}#"
        collapsing_toolbar_layout.title = categoryData?.name
        collapsing_toolbar_layout.setExpandedTitleColor(Color.WHITE)
        collapsing_toolbar_layout.setCollapsedTitleTextColor(Color.BLACK)

        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mAdapter
        //实现自动加载
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val itemCount = mRecyclerView.layoutManager.itemCount
                val lastVisibleItem = (mRecyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (!loadingMore && lastVisibleItem == (itemCount - 1)) {
                    loadingMore = true
                    mPresenter.loadMoreData()
                }
            }
        })


//状态栏透明和间距处理
        StatusBarUtil.darkMode(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
    }

    override fun start() {
        mPresenter.getCategoryDetailList(categoryData?.id!!)
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    override fun setCateDetailList(itemList: ArrayList<HomeBean.Issue.Item>) {
        loadingMore = false
        mAdapter.addData(itemList)
    }

    override fun showError(errorMsg: String) {
        multipleStatusView.showError()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}