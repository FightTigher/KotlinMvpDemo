package com.ecovacs.kotlinmvpdemo.ui.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ecovacs.kotlinmvpdemo.R
import com.ecovacs.kotlinmvpdemo.glide.GlideApp
import com.ecovacs.kotlinmvpdemo.mvp.model.bean.HomeBean
import com.ecovacs.kotlinmvpdemo.view.recyclerview.MultipleType
import com.ecovacs.kotlinmvpdemo.view.recyclerview.ViewHolder
import com.ecovacs.kotlinmvpdemo.view.recyclerview.adapter.CommonAdapter

/**
 * Created by liang.liu on 2018/2/1.
 */
class FollowAdapter(mContext: Context, data: ArrayList<HomeBean.Issue.Item>)
    : CommonAdapter<HomeBean.Issue.Item>(mContext, data, object : MultipleType<HomeBean.Issue.Item> {
    override fun getLayoutId(item: HomeBean.Issue.Item, position: Int): Int {
        return when{
            item.type == "videoCollectionWithBrief" ->
                    R.layout.item_follow
            else ->
                    throw IllegalAccessException("Api 出错")
        }
    }
}) {
    fun addData(dataList: ArrayList<HomeBean.Issue.Item>) {
        this.mData.addAll(dataList)
        notifyDataSetChanged()
    }

    override fun bindData(holder: ViewHolder, data: HomeBean.Issue.Item, position: Int) {
        when {
            data.type == "videoCollectionWithBrief" -> setAuthorInfo(data, holder)
        }
    }


    private fun setAuthorInfo(item: HomeBean.Issue.Item, holder: ViewHolder) {
        val headerData = item.data?.header
        /**
         * 加载作者头像
         */
        holder.setImagePath(R.id.iv_avatar, object : ViewHolder.HolderImageLoader(headerData?.icon!!) {
            override fun loadImage(iv: ImageView, path: String) {
                GlideApp.with(mContext)
                        .load(path)
                        .placeholder(R.mipmap.default_avatar).circleCrop()
                        .transition(DrawableTransitionOptions().crossFade())
                        .into(holder.getView(R.id.iv_avatar))
            }

        })
        holder.setText(R.id.tv_title, headerData.title)
        holder.setText(R.id.tv_desc, headerData.description)

        val recyclerView = holder.getView<RecyclerView>(R.id.fl_recyclerView)
        /**
         * 设置嵌套水平的 RecyclerView
         */
        recyclerView.layoutManager = LinearLayoutManager(mContext as Activity, LinearLayoutManager.HORIZONTAL,false)
        recyclerView.adapter = FollowHorizontalAdapter(mContext,item.data.itemList,R.layout.item_follow_horizontal)

    }
}