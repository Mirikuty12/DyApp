package com.dynamicyield.templates.ui.quickactions

import android.content.Context
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import coil.load
import com.dynamicyield.templates.R
import com.dynamicyield.templates.ui.base.data.ImageScaleType
import com.dynamicyield.templates.ui.base.util.createRectDrawable
import com.dynamicyield.templates.ui.base.util.dpToPx
import com.dynamicyield.templates.ui.base.util.parseColorOrNull

class FeaturedQuickActionView : ConstraintLayout {

    private lateinit var actionContainer: ConstraintLayout
    private lateinit var titleTv: TextView
    private lateinit var imageView: ImageView

    constructor(context: Context) : super(context) {
        dyViewInit(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        dyViewInit(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        dyViewInit(context, attrs, defStyleAttr)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        dyViewInit(context, attrs, defStyleAttr, defStyleRes)
    }

    private fun dyViewInit(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
    ) {
        inflate(context, R.layout.featured_quick_action_layout, this)

        // init internal variables
        actionContainer = findViewById(R.id.actionContainer)
        titleTv = findViewById(R.id.titleTv)
        imageView = findViewById(R.id.imageView)

        // background

    }

    fun setTitle(text: CharSequence?) {
        titleTv.text = text
        titleTv.visibility = if (text.isNullOrBlank()) View.INVISIBLE else View.VISIBLE
    }

    fun setTitleColor(colorStr: String) {
        val color = colorStr.parseColorOrNull() ?: return
        titleTv.setTextColor(color)
    }

    fun setTitleSize(size: Float) {
        titleTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
    }

    fun setImage(url: String?, scaleType: ImageScaleType = ImageScaleType.FIT) {
        imageView.scaleType = when (scaleType) {
            ImageScaleType.FIT -> ImageView.ScaleType.FIT_CENTER
            ImageScaleType.FILL -> ImageView.ScaleType.CENTER_CROP
        }
        imageView.load(url) {
            crossfade(true)
        }
    }

    fun setupBackground(
        backgroundColor: String? = null,
        borderColor: String? = null,
        borderWidth: Int = 0,
        cornerRadius: Int = 16,
        pressedBackgroundColor: String? = null,
        pressedBorderColor: String? = borderColor,
        pressedBorderWidth: Int = borderWidth,
        pressedCornerRadius: Int = cornerRadius,
    ) {
        val defaultDrawable = createRectDrawable(
            fillColor = backgroundColor.parseColorOrNull(),
            strokeColor = borderColor.parseColorOrNull(),
            strokeWidthPx = borderWidth.toFloat().dpToPx(),
            cornerRadiusPx = cornerRadius.toFloat().dpToPx()
        )

        val pressedDrawable = createRectDrawable(
            fillColor = pressedBackgroundColor.parseColorOrNull(),
            strokeColor = pressedBorderColor.parseColorOrNull(),
            strokeWidthPx = pressedBorderWidth.toFloat().dpToPx(),
            cornerRadiusPx = pressedCornerRadius.toFloat().dpToPx()
        )

        actionContainer.background = StateListDrawable().apply {
            addState(intArrayOf(android.R.attr.state_pressed), pressedDrawable)
            addState(intArrayOf(), defaultDrawable)
        }
    }

    fun setActionClickListener(l : OnClickListener?) {
        actionContainer.setOnClickListener(l)
    }
}