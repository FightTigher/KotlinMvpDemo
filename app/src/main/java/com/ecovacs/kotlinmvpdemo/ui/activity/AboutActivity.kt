package com.ecovacs.kotlinmvpdemo.ui.activity

import android.content.Intent
import android.net.Uri
import com.ecovacs.kotlinmvpdemo.MyApplication
import com.ecovacs.kotlinmvpdemo.R
import com.ecovacs.kotlinmvpdemo.base.BaseActivity
import com.ecovacs.kotlinmvpdemo.utils.AppUtils
import com.ecovacs.kotlinmvpdemo.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_about.*

/**
 * Created by liang.liu on 2018/2/11.
 */
class AboutActivity : BaseActivity() {


    override fun layoutId(): Int = R.layout.activity_about

    override fun initData() {

    }

    override fun initView() {
        StatusBarUtil.darkMode(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)

        tv_version_name.text = "v${AppUtils.getVerName(MyApplication.context)}"
        //返回
        toolbar.setNavigationOnClickListener { finish() }
        //访问 GitHub
        relayout_gitHub.setOnClickListener {
            val uri = Uri.parse("https://github.com/FightTigher/KotlinMvpDemo")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }

    override fun start() {

    }
}