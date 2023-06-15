package com.capstone.moru.ui.customview

import android.R.style
import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.capstone.moru.R

class CustomStatus : androidx.appcompat.widget.AppCompatTextView {
    private var status: String = "not started"

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()

    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()

    }


    private fun init() {
        text = resources.getString(R.string.status_not_started)
        setTextColor(ContextCompat.getColor(context, R.color.blue))
    }

    fun setStatus(status: String) {
        this.status = status
        updateStatus()
    }

    private fun updateStatus() {
        when (status) {
            "COMPLETED" -> {
                text = resources.getString(R.string.status_completed)
                setTextColor(ContextCompat.getColor(context, R.color.green))
            }
            "SKIPPED" -> {
                text = resources.getString(R.string.status_skipped)
                setTextColor(ContextCompat.getColor(context, R.color.red))
            }
            "NOT_STARTED" -> {
                text = resources.getString(R.string.status_not_started)
                setTextColor(ContextCompat.getColor(context, R.color.blue))
            }
        }
    }
}