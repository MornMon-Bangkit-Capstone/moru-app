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

    private val path = Path()
    private val rect = RectF()

    private val cornerRadius = 12f

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

    fun setCategory(category: String) {
        this.category = category
        updateCategory()
    }

    override fun onDraw(canvas: Canvas?) {

        rect.set(0f, 0f, width.toFloat(), height.toFloat())
        path.addRoundRect(rect, cornerRadius, cornerRadius, Path.Direction.CW)

        canvas?.clipPath(path)

        super.onDraw(canvas)
    }

    private fun init() {

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