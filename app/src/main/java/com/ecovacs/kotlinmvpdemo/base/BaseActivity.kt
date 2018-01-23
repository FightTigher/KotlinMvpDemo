package com.ecovacs.kotlinmvpdemo.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.classic.common.MultipleStatusView
import com.ecovacs.kotlinmvpdemo.MyApplication

/**
 * Created by liang.liu on 2018/1/23.
 */
abstract class BaseActivity : AppCompatActivity() {
    /**
     * 多种状态的 View 的切换
     */
    protected var mLayoutStatusView: MultipleStatusView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        initData()
        initView()
        start()
        initListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        MyApplication.getRefWatcher(this)?.watch(this)
    }


    private fun initListener() {
        mLayoutStatusView?.setOnRetryClickListener(mRetryClickListener)
    }

    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        start()
    }

    abstract fun start()

    abstract fun initView()

    abstract fun initData()

    abstract fun getLayout(): Int

    fun openKeyBord(mEditText: EditText, mContext: Context) {
        val ims = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        ims.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN)
        ims.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    fun closeKeyBoard(mEditText: EditText, mContext: Context) {
        val ims = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        ims.hideSoftInputFromWindow(mEditText.windowToken, 0)
    }
}