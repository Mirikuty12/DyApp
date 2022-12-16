package com.dynamicyield.templates.ui.offers

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.doOnLayout
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import coil.load
import com.dynamicyield.templates.R
import com.dynamicyield.templates.ui.base.data.ImageScaleType
import com.dynamicyield.templates.ui.base.util.createGradientRectDrawable
import com.dynamicyield.templates.ui.base.util.createRectDrawable
import com.dynamicyield.templates.ui.base.util.dpToPx
import com.dynamicyield.templates.ui.base.util.parseColorOrNull
import com.google.android.material.imageview.ShapeableImageView
import kotlin.math.max

class OfferView : CardView {

    private lateinit var contentLayout: ConstraintLayout
    private lateinit var backgroundFrameLayout: FrameLayout
    private lateinit var backgroundImageView: ImageView
    private lateinit var logoShapeableImageView: ShapeableImageView
    private lateinit var labelTextView: TextView
    private lateinit var titleTextView: TextView


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
        inflate(context, R.layout.offer_layout, this)

        // init internal variables
        contentLayout = findViewById(R.id.contentLayout)
        backgroundFrameLayout = findViewById(R.id.backgroundFrameLayout)
        backgroundImageView = findViewById(R.id.backgroundImageView)
        logoShapeableImageView = findViewById(R.id.logoShapeableImageView)
        labelTextView = findViewById(R.id.labelTextView)
        titleTextView = findViewById(R.id.titleTextView)

        setCornerRadius(16f)

        // adjust dim effect
        adjustDimEffect()

        // adjust the round shape of the logo
        logoShapeableImageView.shapeAppearanceModel = logoShapeableImageView.shapeAppearanceModel
            .toBuilder().apply {
                setAllCornerSizes { rect -> max(rect.width(), rect.height()) / 2 }
            }.build()

        // remove the shadow that appeared because of the elevation
        logoShapeableImageView.outlineProvider = null
        labelTextView.outlineProvider = null
        titleTextView.outlineProvider = null
    }

    fun setCornerRadius(radiusDp: Float) {
        radius = radiusDp.dpToPx().toFloat()
    }

    fun setBackgroundImage(url: String?, scaleType: ImageScaleType = ImageScaleType.FIT) {
        backgroundImageView.scaleType = when (scaleType) {
            ImageScaleType.FIT -> ImageView.ScaleType.FIT_CENTER
            ImageScaleType.FILL -> ImageView.ScaleType.CENTER_CROP
        }
        backgroundImageView.load(url) {
            crossfade(true)
        }
    }

    fun setLogoImage(url: String?, scaleType: ImageScaleType = ImageScaleType.FIT) {
        logoShapeableImageView.scaleType = when (scaleType) {
            ImageScaleType.FIT -> ImageView.ScaleType.FIT_CENTER
            ImageScaleType.FILL -> ImageView.ScaleType.CENTER_CROP
        }
        logoShapeableImageView.load(url) {
            crossfade(true)
        }
    }

    fun setLabel(text: String?, textColor: String, textSize: Float, backgroundColor: String) {
        labelTextView.isVisible = !text.isNullOrBlank()
        labelTextView.text = text
        labelTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
        textColor.parseColorOrNull()?.let { labelTextView.setTextColor(it) }
        labelTextView.doOnLayout { labelTv ->
            labelTv.background = createRectDrawable(
                fillColor = backgroundColor.parseColorOrNull(),
                cornerRadiusPx = labelTv.height
            )
        }
    }

    fun setTitle(text: String?, textColor: String, textSize: Float) {
        titleTextView.isVisible = !text.isNullOrBlank()
        titleTextView.text = text
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
        textColor.parseColorOrNull()?.let { titleTextView.setTextColor(it) }
    }

    fun setOfferClickListener(listener: OnClickListener) {
        backgroundFrameLayout.setOnClickListener(listener)
    }

    fun setOfferViewMode(mode: OfferViewMode) {
        backgroundImageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
            dimensionRatio = when (mode) {
                OfferViewMode.RECTANGLE -> "3:2"
                OfferViewMode.SQUARE -> "1:1"
            }
        }
    }

    private fun adjustDimEffect() {
        val defaultColors = listOf("#00000000", "#00000000", "#B3070707").mapNotNull { it.parseColorOrNull() }
        val pressedColor = "#52070707".parseColorOrNull() ?: return

        val defaultDrawable = createGradientRectDrawable(defaultColors.toIntArray())
        val pressedDrawable = ColorDrawable(pressedColor)

        backgroundFrameLayout.foreground = StateListDrawable().apply {
            addState(intArrayOf(android.R.attr.state_pressed), pressedDrawable)
            addState(intArrayOf(), defaultDrawable)
        }
    }

    enum class OfferViewMode(val valueStr: String) {
        RECTANGLE("rectangle"),
        SQUARE("square");

        companion object {
            fun fromString(value: String) = when (value) {
                RECTANGLE.valueStr -> RECTANGLE
                SQUARE.valueStr -> SQUARE
                else -> null
            }
        }
    }
}