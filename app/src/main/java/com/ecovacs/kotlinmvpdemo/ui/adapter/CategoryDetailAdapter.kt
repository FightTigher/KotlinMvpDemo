package com.ecovacs.kotlinmvpdemo.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.view.View
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.ecovacs.kotlinmvpdemo.Constants
import com.ecovacs.kotlinmvpdemo.R
import com.ecovacs.kotlinmvpdemo.durationFormat
import com.ecovacs.kotlinmvpdemo.glide.GlideApp
import com.ecovacs.kotlinmvpdemo.mvp.model.bean.HomeBean
import com.ecovacs.kotlinmvpdemo.ui.activity.VideoDetailActivity
import com.ecovacs.kotlinmvpdemo.view.recyclerview.ViewHolder
import com.ecovacs.kotlinmvpdemo.view.recyclerview.adapter.CommonAdapter

/**
 * Created by liang.liu on 2018/2/8.
 */
public class CategoryDetailAdapter(context: Context, dateList: ArrayList<HomeBean.Issue.Item>, layoutID: Int)
    : CommonAdapter<HomeBean.Issue.Item>(context, dateList, layoutID) {


    override fun bindData(holder: ViewHolder, data: HomeBean.Issue.Item, position: Int) {
        setVideoItem(holder, data)
    }

    private fun setVideoItem(holder: ViewHolder, data: HomeBean.Issue.Item) {
        val itemData = data.data

        val cover = itemData?.cover?.feed ?: ""

        GlideApp.with(mContext)
                .load(cover)
                .apply(RequestOptions().placeholder(R.drawable.placeholder_banner))
                .transition(DrawableTransitionOptions().crossFade())
                .into(holder.getView(R.id.iv_image))

        holder.setText(R.id.tv_title, itemData?.title ?: "")

        // 格式化时间
        val timeFormat = durationFormat(itemData?.duration)

        holder.setText(R.id.tv_tag, "#${itemData?.category}/$timeFormat")

        holder.setOnItemClickListener(listener = View.OnClickListener {
            goToVideoPlayer(mContext as Activity, holder.getView(R.id.iv_image), data)
        })
    }

    private fun goToVideoPlayer(activity: Activity, view: View, itemData: HomeBean.Issue.Item) {
        val intent = Intent(activity, VideoDetailActivity::class.java)

        intent.putExtra(Constants.BUNDLE_VIDEO_DATA, itemData)
        intent.putExtra(VideoDetailActivity.Companion.TRANSITION, true)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val pair = android.support.v4.util.Pair<View, String>(view, VideoDetailActivity.IMG_TRANSITION)
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pair)

            ActivityCompat.startActivity(activity, intent, activityOptions.toBundle())
        } else {
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }

    fun addData(itemList: ArrayList<HomeBean.Issue.Item>) {
        this.mData.addAll(itemList)
        notifyDataSetChanged()
    }

}