package com.ecovacs.kotlinmvpdemo.mvp.model.bean

import com.google.gson.annotations.SerializedName


data class TabInfoBean(@SerializedName("tabInfo") val tabInfo: TabInfo) {
    data class TabInfo(
            @SerializedName("tabList") val tabList: List<Tab>,
            @SerializedName("defaultIdx") val defaultIdx: Int
    )

    data class Tab(
            @SerializedName("id") val id: Int, //0
            @SerializedName("name") val name: String, //周排行
            @SerializedName("apiUrl") val apiUrl: String //http://baobab.kaiyanapp.com/api/v4/rankList/videos?strategy=weekly
    )
}




