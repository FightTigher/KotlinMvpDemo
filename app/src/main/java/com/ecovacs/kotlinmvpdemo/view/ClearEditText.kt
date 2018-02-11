package com.ecovacs.kotlinmvpdemo.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import com.ecovacs.kotlinmvpdemo.R

/**
 * Created by liang.liu on 2018/2/11.
 */
class ClearEditText @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
                                              defStyle: Int = android.R.attr.editTextStyle)
    : EditText(context, attrs, defStyle), View.OnFocusChangeListener, TextWatcher {

    private var mClearDrawable: Drawable? = null
    private var hasFocus: Boolean = false

    init {
        init()
    }

    private fun init() {
        mClearDrawable = compoundDrawables[2]
        if (mClearDrawable == null) {
            mClearDrawable = resources.getDrawable(R.mipmap.ic_action_clear)
        }

        mClearDrawable!!.setBounds(0, 0, mClearDrawable!!.intrinsicWidth,
                mClearDrawable!!.intrinsicHeight)

        setClearIconVisible(false)

        onFocusChangeListener = this

        addTextChangedListener(this)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            if (compoundDrawables[2] != null) {
                val x = event.x.toInt()
                val y = event.y.toInt()
                val rect = compoundDrawables[2].bounds
                val height = rect.height()
                val distance = (getHeight() - height) / 2
                val isInnerWidth = x > width - totalPaddingRight && x < width - paddingRight
                val isInnerHeight = y > distance && y < distance + height
                if (isInnerWidth && isInnerHeight) {
                    this.setText("")
                }
            }
        }

        return super.onTouchEvent(event)
    }

    private fun setClearIconVisible(visible: Boolean) {
        val right = if (visible) mClearDrawable else null
        setCompoundDrawables(compoundDrawables[0], compoundDrawables[1], right, compoundDrawables[3])
    }


    override fun onFocusChange(v: View, hasFocus: Boolean) {
        this.hasFocus = hasFocus
        if (hasFocus) {
            setClearIconVisible(text.isNotEmpty())
        } else {
            setClearIconVisible(false)
        }
    }

    override fun onTextChanged(text: CharSequence, start: Int, lengthBefore: Int, lengthAfter: Int) {
        if (hasFocus){
            setClearIconVisible(text.isNotEmpty())
        }
    }

    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

}