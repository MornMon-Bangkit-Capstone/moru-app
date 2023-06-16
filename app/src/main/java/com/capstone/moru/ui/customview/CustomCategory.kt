package com.capstone.moru.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.RectF
import android.util.AttributeSet
import android.graphics.Path
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import com.capstone.moru.R

class CustomCategory : androidx.appcompat.widget.AppCompatTextView {
    companion object {
        const val ALPHA_OPACITY = 64
    }

    private var category: String = ""

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

    override fun onDraw(canvas: Canvas?) {

        super.onDraw(canvas)
    }

    private fun init() {
        setTextColor(ContextCompat.getColor(context, R.color.orange))
        setBackgroundColor(
            ColorUtils.setAlphaComponent(
                ContextCompat.getColor(
                    context,
                    R.color.orange
                ), ALPHA_OPACITY
            )
        )

    }

    private fun updateCategory() {
        when (category) {
            /*

                Isi category lain (disesuaikan dengan data yang ada)

            */
            else -> {
                text = resources.getString(R.string.category_default)
                setTextColor(ContextCompat.getColor(context, R.color.blue))
                setBackgroundColor(
                    ColorUtils.setAlphaComponent(
                        ContextCompat.getColor(
                            context,
                            R.color.blue
                        ), ALPHA_OPACITY
                    )
                )
            }
        }
    }


}