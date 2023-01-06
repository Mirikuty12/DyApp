package com.dynamicyield.templates.ui.refinance

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.doOnLayout
import androidx.core.view.isVisible
import coil.load
import com.dynamicyield.templates.R
import com.dynamicyield.templates.core.DyApplication
import com.dynamicyield.templates.ui.DyWidget
import com.dynamicyield.templates.ui.DyWidgetName
import com.dynamicyield.templates.ui.base.data.ImageScaleType
import com.dynamicyield.templates.ui.base.util.createRectDrawable
import com.dynamicyield.templates.ui.base.util.dpToPx
import com.dynamicyield.templates.ui.base.util.parseColorOrNull

class RefinanceView : CardView, DyWidget {

    private var refinanceListener: RefinanceListener? = null

    private lateinit var contentContainer: ConstraintLayout
    private lateinit var imageView: ImageView
    private lateinit var titleTv: TextView
    private lateinit var subtitleTv: TextView
    private lateinit var ctaButton1: Button
    private lateinit var ctaButton2: Button

    override val dyName = DyWidgetName.Refinance

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

    private fun dyViewInit(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) {
        inflate(context, R.layout.refinance_layout, this)

        // init internal view
        contentContainer = findViewById(R.id.contentContainer)
        imageView = findViewById(R.id.imageView)
        titleTv = findViewById(R.id.titleTv)
        subtitleTv = findViewById(R.id.subtitleTv)
        ctaButton1 = findViewById(R.id.ctaButton1)
        ctaButton2 = findViewById(R.id.ctaButton2)

        // set corners
        setCornerRadius(16f)
    }

    fun setCornerRadius(radiusDp: Float) {
        radius = radiusDp.dpToPx().toFloat()
    }

    fun setBackgroundColorStr(colorStr: String) {
        val color = colorStr.parseColorOrNull() ?: return
        contentContainer.setBackgroundColor(color)
    }

    fun setImage(url: String?, scaleType: ImageScaleType = ImageScaleType.FIT) {
        imageView.scaleType = when (scaleType) {
            ImageScaleType.FIT -> ImageView.ScaleType.FIT_CENTER
            ImageScaleType.FILL -> ImageView.ScaleType.CENTER_CROP
        }
        imageView.isVisible = !url.isNullOrBlank()
        imageView.load(data = url, imageLoader = DyApplication.imageLoader(imageView.context))
    }

    fun setTitle(text: String?, textColor: String, textSize: Float) {
        titleTv.isVisible = !text.isNullOrBlank()
        titleTv.text = text
        titleTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
        textColor.parseColorOrNull()?.let { titleTv.setTextColor(it) }
    }

    fun setSubtitle(text: String?, textColor: String, textSize: Float) {
        subtitleTv.isVisible = !text.isNullOrBlank()
        subtitleTv.text = text
        subtitleTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
        textColor.parseColorOrNull()?.let { subtitleTv.setTextColor(it) }
    }

    fun setCtaButton1(
        buttonText: String?,
        buttonTextSizeSp: Float = 18f,
        buttonTextColorString: String = "#2870F6",
        pressedButtonTextColorString: String = buttonTextColorString,
        buttonBackgroundColorString: String = "#FFFFFF",
        pressedButtonBackgroundColorString: String = "#F3F2F2",
        buttonStrokeColorString: String = "#FFFFFF",
        buttonStrokeWidth: Float = 0f,
    ) = with (ctaButton1) {
        isVisible = !buttonText.isNullOrBlank()
        text = buttonText

        setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSizeSp)

        val buttonTextColor = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_pressed),
                intArrayOf()
            ),
            intArrayOf(
                pressedButtonTextColorString.parseColorOrNull() ?: Color.BLACK,
                buttonTextColorString.parseColorOrNull() ?: Color.BLACK
            )
        )
        setTextColor(buttonTextColor)

        setOnClickListener {
            refinanceListener?.onCtaButton1Click(it)
        }

        doOnLayout {
            val defaultDrawable = createRectDrawable(
                fillColor = buttonBackgroundColorString.parseColorOrNull(),
                strokeColor = buttonStrokeColorString.parseColorOrNull(),
                strokeWidthPx = buttonStrokeWidth.dpToPx(),
                cornerRadiusPx = height / 2
            )
            val pressedDrawable = createRectDrawable(
                fillColor = pressedButtonBackgroundColorString.parseColorOrNull(),
                strokeColor = buttonStrokeColorString.parseColorOrNull(),
                strokeWidthPx = buttonStrokeWidth.dpToPx(),
                cornerRadiusPx = height / 2
            )
            background = StateListDrawable().apply {
                addState(intArrayOf(android.R.attr.state_pressed), pressedDrawable)
                addState(intArrayOf(), defaultDrawable)
            }
            backgroundTintList = null
        }
    }

    fun setCtaButton2(
        buttonText: String?,
        buttonTextSizeSp: Float = 18f,
        buttonTextColorString: String = "#2870F6",
        pressedButtonTextColorString: String = buttonTextColorString,
        buttonBackgroundColorString: String = "#FFFFFF",
        pressedButtonBackgroundColorString: String = "#F3F2F2",
        buttonStrokeColorString: String = "#FFFFFF",
        buttonStrokeWidth: Float = 0f,
    ) = with (ctaButton2) {
        isVisible = !buttonText.isNullOrBlank()
        text = buttonText

        setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSizeSp)

        val buttonTextColor = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_pressed),
                intArrayOf()
            ),
            intArrayOf(
                pressedButtonTextColorString.parseColorOrNull() ?: Color.BLACK,
                buttonTextColorString.parseColorOrNull() ?: Color.BLACK
            )
        )
        setTextColor(buttonTextColor)

        setOnClickListener {
            refinanceListener?.onCtaButton2Click(it)
        }

        doOnLayout {
            val defaultDrawable = createRectDrawable(
                fillColor = buttonBackgroundColorString.parseColorOrNull(),
                strokeColor = buttonStrokeColorString.parseColorOrNull(),
                strokeWidthPx = buttonStrokeWidth.dpToPx(),
                cornerRadiusPx = height / 2
            )
            val pressedDrawable = createRectDrawable(
                fillColor = pressedButtonBackgroundColorString.parseColorOrNull(),
                strokeColor = buttonStrokeColorString.parseColorOrNull(),
                strokeWidthPx = buttonStrokeWidth.dpToPx(),
                cornerRadiusPx = height / 2
            )
            background = StateListDrawable().apply {
                addState(intArrayOf(android.R.attr.state_pressed), pressedDrawable)
                addState(intArrayOf(), defaultDrawable)
            }
            backgroundTintList = null
        }
    }

    fun setRefinanceListener(listener: RefinanceListener?) {
        refinanceListener = listener
    }

    interface RefinanceListener {
        fun onCtaButton1Click(v: View)
        fun onCtaButton2Click(v: View)
    }
}