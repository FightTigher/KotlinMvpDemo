package com.ecovacs.kotlinmvpdemo.ui.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.ecovacs.kotlinmvpdemo.MyApplication
import com.ecovacs.kotlinmvpdemo.R
import com.ecovacs.kotlinmvpdemo.durationFormat
import com.ecovacs.kotlinmvpdemo.glide.GlideApp
import com.ecovacs.kotlinmvpdemo.glide.GlideRoundTransform
import com.ecovacs.kotlinmvpdemo.mvp.model.bean.HomeBean
import com.ecovacs.kotlinmvpdemo.view.recyclerview.MultipleType
import com.ecovacs.kotlinmvpdemo.view.recyclerview.ViewHolder
import com.ecovacs.kotlinmvpdemo.view.recyclerview.adapter.CommonAdapter

/**
 * Created by liang.liu on 2018/1/31.
 */
class VideoDetailAdapter(mContext: Context, date: ArrayList<HomeBean.Issue.Item>) :
        CommonAdapter<HomeBean.Issue.Item>(mContext, date, object : MultipleType<HomeBean.Issue.Item> {
            override fun getLayoutId(item: HomeBean.Issue.Item, position: Int): Int {

                return when {
                    position == 0 ->
                        R.layout.item_video_detail_info
                    date[position].type == "textCard" ->
                        R.layout.item_video_text_card
                    date[position].type == "videoSmallCard" ->
                        R.layout.item_video_small_card
                    else -> throw IllegalAccessException("Api 解析出错")
                }
            }

        }) {

    private var textTypeface: Typeface? = null

    init {
        textTypeface = Typeface.createFromAsset(MyApplication.context.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
    }


    /**
     * 添加视频的详细信息
     */
    fun addData(item: HomeBean.Issue.Item) {
        mData.clear()
        notifyDataSetChanged()
        mData.add(item)
        notifyItemInserted(0)

    }

    /**
     * 添加相关推荐等数据 Item
     */
    fun addData(item: ArrayList<HomeBean.Issue.Item>) {
        mData.addAll(item)
        notifyItemRangeInserted(1, item.size)

    }

    /**
     * Kotlin的函数可以作为参数，写callback的时候，可以不用interface了
     */

    private var mOnItemClickRelatedVideo: ((item: HomeBean.Issue.Item) -> Unit)? = null


    fun setOnItemDetailClick(mItemRelatedVideo: (item: HomeBean.Issue.Item) -> Unit) {
        this.mOnItemClickRelatedVideo = mItemRelatedVideo
    }

    override fun bindData(holder: ViewHolder, data: HomeBean.Issue.Item, position: Int) {
        when {
            position == 0 -> setVideoDetailInfo(data, holder)

            data.type == "textCard" -> {
                holder.setText(R.id.tv_text_card, data.data?.text!!)

                holder.getView<TextView>(R.id.tv_text_card).typeface = textTypeface
            }

            data.type == "videoSmallCard" -> {
                with(holder) {
                    setText(R.id.tv_title, data.data?.title!!)
                    setText(R.id.tv_tag, "#${data.data.category} / ${durationFormat(data.data.duration)}")
                    setImagePath(R.id.iv_video_small_card, object : ViewHolder.HolderImageLoader(data.data.cover.detail) {
                        override fun loadImage(iv: ImageView, path: String) {
                            GlideApp.with(mContext)
                                    .load(path)
                                    .optionalTransform(GlideRoundTransform())
                                    .placeholder(R.drawable.placeholder_banner)
                                    .into(iv)
                        }
                    })
                }

                // 判断onItemClickRelatedVideo 并使用
                holder.itemView.setOnClickListener { mOnItemClickRelatedVideo?.invoke(data) }
            }

            else -> throw IllegalAccessException("Api 解析出错了，出现其他类型")
        }
    }

    private fun setVideoDetailInfo(data: HomeBean.Issue.Item, holder: ViewHolder) {
        data.data?.title?.let { holder.setText(R.id.tv_title, it) }
        //视频简介
        data.data?.description?.let { holder.setText(R.id.expandable_text, it) }
        //标签
        holder.setText(R.id.tv_tag, "#${data.data?.category} / ${durationFormat(data.data?.duration)}")
        //喜欢
        holder.setText(R.id.tv_action_favorites, data.data?.consumption?.collectionCount.toString())
        //分享
        holder.setText(R.id.tv_action_share, data.data?.consumption?.shareCount.toString())
        //评论
        holder.setText(R.id.tv_action_reply, data.data?.consumption?.replyCount.toString())

        if (data.data?.author != null) {
            with(holder) {
                setText(R.id.tv_author_name, data.data.author.name)
                setText(R.id.tv_author_desc, data.data.author.description)
                setImagePath(R.id.iv_avatar, object : ViewHolder.HolderImageLoader(data.data.author.icon) {
                    override fun loadImage(iv: ImageView, path: String) {
                        GlideApp.with(mContext)
                                .load(path)
                                .placeholder(R.mipmap.default_avatar)
                                .centerCrop()
                                .into(iv)
                    }
                })
            }
        } else {
            holder.setViewVisibilty(R.id.layout_author_view, View.GONE)
        }

        with(holder) {
            getView<TextView>(R.id.tv_action_favorites).setOnClickListener {
                Toast.makeText(MyApplication.context, "喜欢", Toast.LENGTH_SHORT).show()
            }
            getView<TextView>(R.id.tv_action_share).setOnClickListener {
                Toast.makeText(MyApplication.context, "分享", Toast.LENGTH_SHORT).show()
            }
            getView<TextView>(R.id.tv_action_reply).setOnClickListener {
                Toast.makeText(MyApplication.context, "评论", Toast.LENGTH_SHORT).show()
            }
        }

    }

}














