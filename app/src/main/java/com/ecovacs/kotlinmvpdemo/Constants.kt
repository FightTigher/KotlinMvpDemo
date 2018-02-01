package com.ecovacs.kotlinmvpdemo

/**
 * Created by liang.liu on 2018/1/31.
 */
class Constants private constructor() {

    companion object {

        val BUNDLE_VIDEO_DATA = "video_data"
        val BUNDLE_CATEGORY_DATA = "category_data"

        //腾讯bugly app id
        val BUGLY_APPID = "f3aadf97d4"


        //sp 存储的文件名
        val FILE_WATCH_HISTORY_NAME = "watch_history_file"  //观看记录

        val FILE_COLLECTION_NAME = "collection_file"  //收藏视频缓存文件名
    }
}