package com.ecovacs.kotlinmvpdemo.view.recyclerview.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ecovacs.kotlinmvpdemo.view.recyclerview.MultipleType
import com.ecovacs.kotlinmvpdemo.view.recyclerview.ViewHolder

/**
 * Created by liang.liu on 2018/1/28.
 */
abstract class CommonAdapter<T>(var mContext: Context, var mData: ArrayList<T>,
                                private var mLayoutId: Int) : RecyclerView.Adapter<ViewHolder>() {


    private var typeSupport: MultipleType<T>? = null

    private var mItemClickListener: OnItemClickListener? = null
    private var mItemLongClickListener: OnItemLongClickListener? = null


    constructor(context: Context, mData: ArrayList<T>, typeSupport: MultipleType<T>) : this(context, mData, -1) {
        this.typeSupport = typeSupport
    }


    override fun getItemCount(): Int = mData.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (typeSupport != null) {
            mLayoutId = viewType
        }

        val view = LayoutInflater.from(mContext).inflate(mLayoutId, parent, false)
        return ViewHolder(view!!)
    }

    override fun getItemViewType(position: Int): Int {
        return typeSupport?.getLayoutId(mData[position], position)
                ?: super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bindData(holder, mData[position], position)

        mItemClickListener?.let {
            holder.itemView.setOnClickListener { mItemClickListener!!.onItemClick(mData[position], position) }
        }

        mItemLongClickListener?.let {
            holder.itemView.setOnLongClickListener { mItemLongClickListener!!.onItemLongClick(mData[position], position) }
        }
    }

    abstract fun bindData(holder: ViewHolder, data: T, position: Int)

    fun setOnItemClickListener(itemClickListener: OnItemClickListener) {
        this.mItemClickListener = itemClickListener
    }

    fun setOnItemLongClickListener(itemLongClickListener: OnItemLongClickListener) {
        this.mItemLongClickListener = itemLongClickListener
    }
}