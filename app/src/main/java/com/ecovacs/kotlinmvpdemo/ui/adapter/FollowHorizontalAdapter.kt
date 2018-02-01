package com.ecovacs.kotlinmvpdemo.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ecovacs.kotlinmvpdemo.Constants
import com.ecovacs.kotlinmvpdemo.R
import com.ecovacs.kotlinmvpdemo.durationFormat
import com.ecovacs.kotlinmvpdemo.glide.GlideApp
import com.ecovacs.kotlinmvpdemo.mvp.model.bean.HomeBean
import com.ecovacs.kotlinmvpdemo.ui.activity.VideoDetailActivity
import com.ecovacs.kotlinmvpdemo.view.recyclerview.ViewHolder
import com.ecovacs.kotlinmvpdemo.view.recyclerview.adapter.CommonAdapter
import com.orhanobut.logger.Logger

/**
 * Created by liang.liu on 2018/2/1.
 */
class FollowHorizontalAdapter(mContext: Context, mData: ArrayList<HomeBean.Issue.Item>, layoutId: Int)
    : CommonAdapter<HomeBean.Issue.Item>(mContext, mData, layoutId) {


    override fun bindData(holder: ViewHolder, data: HomeBean.Issue.Item, position: Int) {
        val horizontalItemData = data.data
        holder.setImagePath(R.id.iv_cover_feed, object : ViewHolder.HolderImageLoader(data.data?.cover?.feed!!) {
            override fun loadImage(iv: ImageView, path: String) {
                // 加载封页图
                GlideApp.with(mContext)
                        .load(path)
                        .placeholder(R.drawable.placeholder_banner)
                        .transition(DrawableTransitionOptions().crossFade())
                        .into(holder.getView(R.id.iv_cover_feed))
            }

        })

        //横向 RecyclerView 封页图下面标题
        holder.setText(R.id.tv_title, horizontalItemData?.title ?: "")

        // 格式化时间
        val timeFormat = durationFormat(horizontalItemData?.duration)
        //标签
        with(holder) {
            Logger.d("horizontalItemData===title:${horizontalItemData?.title}tag:${horizontalItemData?.tags?.size}")

            if (horizontalItemData?.tags != null && horizontalItemData.tags.size > 0) {
                setText(R.id.tv_tag, "#${horizontalItemData.tags[0].name} / $timeFormat")
            }else{
                setText(R.id.tv_tag,"#$timeFormat")
            }

            setOnItemClickListener(listener = View.OnClickListener {
                goToVideoPlayer(mContext as Activity, holder.getView(R.id.iv_cover_feed), data)
            })
        }
    }

    /**
     * 跳转到视频详情页面播放
     *
     * @param activity
     * @param view
     */
    private fun goToVideoPlayer(activity: Activity, view: View, itemData: HomeBean.Issue.Item) {
        val intent = Intent(activity, VideoDetailActivity::class.java)
        intent.putExtra(Constants.BUNDLE_VIDEO_DATA, itemData)
        intent.putExtra(VideoDetailActivity.Companion.TRANSITION, true)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val pair = Pair<View, String>(view, VideoDetailActivity.IMG_TRANSITION)
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity, pair)
            ActivityCompat.startActivity(activity, intent, activityOptions.toBundle())
        } else {
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }
}