package com.ecovacs.kotlinmvpdemo.ui.activity

import android.support.v7.widget.LinearLayoutManager
import com.ecovacs.kotlinmvpdemo.Constants
import com.ecovacs.kotlinmvpdemo.MyApplication
import com.ecovacs.kotlinmvpdemo.R
import com.ecovacs.kotlinmvpdemo.base.BaseActivity
import com.ecovacs.kotlinmvpdemo.mvp.model.bean.HomeBean
import com.ecovacs.kotlinmvpdemo.ui.adapter.WatchHistoryAdapter
import com.ecovacs.kotlinmvpdemo.utils.StatusBarUtil
import com.ecovacs.kotlinmvpdemo.utils.WatchHistoryUtils
import kotlinx.android.synthetic.main.activity_video_detail.*
import kotlinx.android.synthetic.main.fragment_hot.*
import java.util.*

/**
 * Created by liang.liu on 2018/2/11.
 */
class WatchHistoryActivity : BaseActivity() {

    private var itemListData = ArrayList<HomeBean.Issue.Item>()

    companion object {
        private val HISTORY_MAX = 20
    }

    override fun layoutId(): Int = R.layout.layout_watch_history

    override fun initData() {
        multipleStatusView.showLoading()
        itemListData = queryWatchHistory()
    }

    private fun queryWatchHistory(): ArrayList<HomeBean.Issue.Item> {
        val watchList = ArrayList<HomeBean.Issue.Item>()
        val hisAll = WatchHistoryUtils.getAll(Constants.FILE_WATCH_HISTORY_NAME, MyApplication.context) as Map<*, *>
        //将key排序升序
        val keys = hisAll.keys.toTypedArray()
        Arrays.sort(keys)
        val keyLength = keys.size
        //这里计算 如果历史记录条数是大于 可以显示的最大条数，则用最大条数做循环条件，防止历史记录条数-最大条数为负值，数组越界
        val hisLength = if (keyLength > HISTORY_MAX) HISTORY_MAX else keyLength
        // 反序列化和遍历 添加观看的历史记录
        (1..hisLength).mapTo(watchList) {
            WatchHistoryUtils.getObject(Constants.FILE_WATCH_HISTORY_NAME, MyApplication.context,
                    keys[keyLength - it] as String) as HomeBean.Issue.Item
        }

        return watchList
    }

    override fun initView() {
        toolbar.setNavigationOnClickListener { finish() }

        val mAdapter = WatchHistoryAdapter(this, itemListData, R.layout.item_video_small_card)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mAdapter

        if (itemListData.size > 1) {
            multipleStatusView.showContent()
        } else {
            multipleStatusView.showEmpty()
        }

        StatusBarUtil.darkMode(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, mRecyclerView)
    }

    override fun start() {

    }
}