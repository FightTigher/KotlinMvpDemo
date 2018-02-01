package com.ecovacs.kotlinmvpdemo.ui.activity

import android.annotation.TargetApi
import android.content.res.Configuration
import android.os.Build
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.transition.Transition
import android.widget.ImageView
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ecovacs.kotlinmvpdemo.Constants
import com.ecovacs.kotlinmvpdemo.MyApplication
import com.ecovacs.kotlinmvpdemo.R
import com.ecovacs.kotlinmvpdemo.base.BaseActivity
import com.ecovacs.kotlinmvpdemo.glide.GlideApp
import com.ecovacs.kotlinmvpdemo.mvp.contract.VideoDetailContract
import com.ecovacs.kotlinmvpdemo.mvp.model.bean.HomeBean
import com.ecovacs.kotlinmvpdemo.mvp.presenter.VideoDetailPresenter
import com.ecovacs.kotlinmvpdemo.showToast
import com.ecovacs.kotlinmvpdemo.ui.adapter.VideoDetailAdapter
import com.ecovacs.kotlinmvpdemo.utils.CleanLeakUtils
import com.ecovacs.kotlinmvpdemo.utils.StatusBarUtil
import com.ecovacs.kotlinmvpdemo.utils.WatchHistoryUtils
import com.ecovacs.kotlinmvpdemo.view.VideoListener
import com.orhanobut.logger.Logger
import com.scwang.smartrefresh.header.MaterialHeader
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import kotlinx.android.synthetic.main.activity_video_detail.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by liang.liu on 2018/1/31.
 */
class VideoDetailActivity : BaseActivity(), VideoDetailContract.View {


    private val presenter: VideoDetailPresenter by lazy { VideoDetailPresenter() }

    private val mAdapter by lazy { VideoDetailAdapter(this, itemList) }

    private val mFormat by lazy { SimpleDateFormat("yyyyMMddHHmmss"); }

    companion object {
        val IMG_TRANSITION = "IMG_TRANSITION"
        val TRANSITION = "TRANSITION"
    }


    private var isTransition: Boolean = false

    private lateinit var itemData: HomeBean.Issue.Item

    private var transition: Transition? = null

    private var itemList = java.util.ArrayList<HomeBean.Issue.Item>()

    private var orientationUtils: OrientationUtils? = null
    private var isPlay: Boolean = false
    private var isPause: Boolean = false

    override fun layoutId(): Int = R.layout.activity_video_detail


    override fun initData() {
        itemData = intent.getSerializableExtra(Constants.BUNDLE_VIDEO_DATA) as HomeBean.Issue.Item
        isTransition = intent.getBooleanExtra(TRANSITION, false)

        saveWatchVideoHistoryInfo(itemData)
    }

    private fun saveWatchVideoHistoryInfo(watchItem: HomeBean.Issue.Item) {
        //保存之前要先查询sp中是否有该value的记录，有则删除.这样保证搜索历史记录不会有重复条目
        val historyMap = WatchHistoryUtils.getAll(Constants.FILE_WATCH_HISTORY_NAME, MyApplication.context)
                as Map<*, *>

        for ((key, _) in historyMap) {
            if (watchItem == WatchHistoryUtils.getObject(Constants.FILE_WATCH_HISTORY_NAME,
                            MyApplication.context, key as String)) {
                WatchHistoryUtils.remove(Constants.FILE_WATCH_HISTORY_NAME, MyApplication.context, key)
            }
        }
        WatchHistoryUtils.putObject(Constants.FILE_WATCH_HISTORY_NAME,
                MyApplication.context, watchItem, "" + mFormat.format(Date()))
    }

    private var mMaterialHeader: MaterialHeader? = null

    override fun initView() {
        presenter.attachView(this)

        initTransition()
        initVideoViewConfig()

        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mAdapter
        mAdapter.setOnItemDetailClick { presenter.loadVideoInfo(it) }

        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, mVideoView)

        /***  下拉刷新  ***/
        //内容跟随偏移
        mRefreshLayout.setEnableHeaderTranslationContent(true)
        mRefreshLayout.setOnRefreshListener {
            loadVideoInfo()
        }
        mMaterialHeader = mRefreshLayout.refreshHeader as MaterialHeader?
        //打开下拉刷新区域块背景:
        mMaterialHeader?.setShowBezierWave(true)
        //设置下拉刷新主题颜色
        mRefreshLayout.setPrimaryColorsId(R.color.color_light_black, R.color.color_title_bg)
    }

    private fun initVideoViewConfig() {
        //设置旋转
        orientationUtils = OrientationUtils(this, mVideoView)
        //是否旋转
        mVideoView.isRotateViewAuto = false
        //是否可以滑动调整
        mVideoView.setIsTouchWiget(true)

        //增加封面
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        GlideApp.with(this)
                .load(itemData.data?.cover?.feed)
                .centerCrop()
                .into(imageView)
        mVideoView.thumbImageView = imageView

        mVideoView.setStandardVideoAllCallBack(object : VideoListener {
            override fun onPrepared(url: String, vararg objects: Any) {
                super.onPrepared(url, *objects)
                //开始播放了才能旋转和全屏
                orientationUtils?.isEnable = true
                isPlay = true
            }

            override fun onAutoComplete(url: String, vararg objects: Any) {
                super.onAutoComplete(url, *objects)
                Logger.d("***** onAutoPlayComplete **** ")
            }

            override fun onPlayError(url: String, vararg objects: Any) {
                super.onPlayError(url, *objects)
                showToast("播放失败")
            }

            override fun onEnterFullscreen(url: String, vararg objects: Any) {
                super.onEnterFullscreen(url, *objects)
                Logger.d("***** onEnterFullscreen **** ")
            }

            override fun onQuitFullscreen(url: String, vararg objects: Any) {
                super.onQuitFullscreen(url, *objects)
                Logger.d("***** onQuitFullscreen **** ")
                //列表返回的样式判断
                orientationUtils?.backToProtVideo()
            }
        })

        mVideoView.backButton.setOnClickListener({ onBackPressed() })
        mVideoView.fullscreenButton.setOnClickListener {
            //直接横屏
            orientationUtils?.resolveByClick()
            //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
            mVideoView.startWindowFullscreen(this, true, true)
        }

        mVideoView.setLockClickListener { _, lock -> orientationUtils?.isEnable = !lock }
    }

    private fun initTransition() {
        if (isTransition && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition()
            ViewCompat.setTransitionName(mVideoView, IMG_TRANSITION)
            addTransitionListener()
            startPostponedEnterTransition()
        } else {
            loadVideoInfo()
        }

    }

    private fun loadVideoInfo() {
        presenter.loadVideoInfo(itemData)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun addTransitionListener() {
        transition = window.sharedElementEnterTransition
        transition?.addListener(object : Transition.TransitionListener {
            override fun onTransitionEnd(transition: Transition?) {
                Logger.d("onTransitionEnd()------")
                loadVideoInfo()
                transition?.removeListener(this)
            }

            override fun onTransitionResume(transition: Transition?) {
            }

            override fun onTransitionPause(transition: Transition?) {
            }

            override fun onTransitionCancel(transition: Transition?) {
            }

            override fun onTransitionStart(transition: Transition?) {
            }

        })
    }

    override fun start() {

    }

    override fun showLoading() {

    }

    override fun dismissLoading() {
        mRefreshLayout.finishRefresh()
    }

    override fun setVideo(url: String) {
        Logger.d("playUrl:$url")
        mVideoView.setUp(url, false, "")
        //开始自动播放
        mVideoView.startPlayLogic()
    }

    override fun setVideoInfo(itemInfo: HomeBean.Issue.Item) {
        itemData = itemInfo
        mAdapter.addData(itemInfo)
        // 请求相关的最新等视频
        presenter.requestRelatedVideo(itemInfo.data?.id?:0)
    }

    override fun setBackground(url: String) {
        GlideApp.with(this)
                .load(url)
                .centerCrop()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .transition(DrawableTransitionOptions().crossFade())
                .into(mVideoBackground)
    }

    override fun setRecentRelatedVideo(itemList: ArrayList<HomeBean.Issue.Item>) {
        mAdapter.addData(itemList)
        this.itemList = itemList
    }

    override fun setErrorMsg(errorMsg: String) {

    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (isPlay && !isPause) {
            mVideoView.onConfigurationChanged(this, newConfig, orientationUtils)
        }
    }


    /**
     * 监听返回键
     */
    override fun onBackPressed() {
        orientationUtils?.backToProtVideo()
        if (StandardGSYVideoPlayer.backFromWindowFull(this))
            return
        //释放所有
        mVideoView.setStandardVideoAllCallBack(null)
        GSYVideoPlayer.releaseAllVideos()
        if (isTransition && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) run {
            super.onBackPressed()
        } else {
            finish()
            overridePendingTransition(R.anim.fade_out, R.anim.fade_in)
        }
    }

    override fun onResume() {
        super.onResume()
        getCurPlay().onVideoResume()
        isPause = false
    }

    override fun onPause() {
        super.onPause()
        getCurPlay().onVideoPause()
        isPause = true
    }

    override fun onDestroy() {
        CleanLeakUtils.fixInputMethodManagerLeak(this)
        super.onDestroy()
        GSYVideoPlayer.releaseAllVideos()
        orientationUtils?.releaseListener()
        presenter.detachView()
    }

    private fun getCurPlay(): GSYVideoPlayer {
        return if (mVideoView.fullWindowPlayer != null) {
            mVideoView.fullWindowPlayer
        } else mVideoView
    }
}