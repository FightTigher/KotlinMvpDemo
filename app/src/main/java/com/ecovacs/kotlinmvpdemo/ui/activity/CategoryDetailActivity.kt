package com.ecovacs.kotlinmvpdemo.ui.activity

import android.graphics.Color
import com.ecovacs.kotlinmvpdemo.Constants
import com.ecovacs.kotlinmvpdemo.R
import com.ecovacs.kotlinmvpdemo.base.BaseActivity
import com.ecovacs.kotlinmvpdemo.glide.GlideApp
import com.ecovacs.kotlinmvpdemo.mvp.contract.CategoryDetailContract
import com.ecovacs.kotlinmvpdemo.mvp.model.bean.CategoryBean
import com.ecovacs.kotlinmvpdemo.mvp.model.bean.HomeBean
import com.ecovacs.kotlinmvpdemo.mvp.presenter.CategoryDetailPresneter
import kotlinx.android.synthetic.main.activity_category_detail.*

/**
 * Created by liang.liu on 2018/2/1.
 */
class CategoryDetailActivity : BaseActivity(), CategoryDetailContract.View {


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


    }

    override fun start() {
        mPresenter.getCategoryDetailList(categoryData?.id!!)
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    override fun setCateDetailList(itemList: ArrayList<HomeBean.Issue.Item>) {

    }

    override fun showError(errorMsg: String) {

    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}