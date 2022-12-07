package com.dynamicyield.templates.ui.cardpromotion

import android.content.Context
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import coil.load
import com.dynamicyield.templates.R
import com.dynamicyield.templates.ui.DyWidget
import com.dynamicyield.templates.ui.DyWidgetName
import com.dynamicyield.templates.ui.base.data.ImageScaleType
import com.dynamicyield.templates.ui.base.util.createGradientRectDrawable
import com.dynamicyield.templates.ui.base.util.createRectDrawable
import com.dynamicyield.templates.ui.base.util.dpToPx
import com.dynamicyield.templates.ui.base.util.parseColorOrNull
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView

class CardPromotionView : MaterialCardView, DyWidget {

    private lateinit var promotionContentContainer: ConstraintLayout
    private lateinit var promotionImageView: ImageView
    private lateinit var bottomPanelImage: ImageView
    private lateinit var bottomText: MaterialTextView
    private lateinit var bottomBtn: MaterialButton

    override val dyName = DyWidgetName.CreditCardPromotion

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
        inflate(context, R.layout.card_promotion_layout, this)

        // init internal variables
        promotionImageView = findViewById(R.id.promotionImageView)
        bottomText = findViewById(R.id.bottomText)
        bottomBtn = findViewById(R.id.bottomBtn)
        bottomPanelImage = findViewById(R.id.bottomPanelImage)
        promotionContentContainer = findViewById(R.id.promotionContentContainer)

        // set corners
        setCornerRadius(16f)

        // set default gradient background
        setBackgroundGradient("#222433", "#0038353F")

        // setup bottom btn
        setupBottomPanelButtonBackground(
            backgroundColor = "#FFFFFF",
            pressedBackgroundColor = "#F3F2F2",
        )
    }

    fun setCornerRadius(radiusDp: Float) {
        radius = radiusDp.dpToPx().toFloat()
    }

    fun setBackgroundColorStr(colorStr: String) {
        val color = colorStr.parseColorOrNull() ?: return
        promotionContentContainer.setBackgroundColor(color)
    }

    fun setBackgroundGradient(topColorStr: String, bottomColorStr: String) {
        val topColor = topColorStr.parseColorOrNull() ?: return
        val bottomColor = bottomColorStr.parseColorOrNull() ?: return
        promotionContentContainer.background = createGradientRectDrawable(
            intArrayOf(topColor, bottomColor)
        )
    }

    fun setImage(url: String?, scaleType: ImageScaleType = ImageScaleType.FIT) {
        promotionImageView.scaleType = when (scaleType) {
            ImageScaleType.FIT -> ImageView.ScaleType.FIT_CENTER
            ImageScaleType.FILL -> ImageView.ScaleType.CENTER_CROP
        }
        promotionImageView.load(url) {
            crossfade(true)
        }
    }

    fun setBottomPanelColor(colorStr: String) {
        val color = colorStr.parseColorOrNull() ?: return
        bottomPanelImage.setBackgroundColor(color)
    }

    fun setBottomPanelText(text: CharSequence?) {
        bottomText.text = text
        bottomText.visibility = if (text.isNullOrBlank()) View.INVISIBLE else View.VISIBLE
    }

    fun setBottomPanelTextColor(colorStr: String) {
        val color = colorStr.parseColorOrNull() ?: return
        bottomText.setTextColor(color)
    }

    fun setBottomPanelTextSize(size: Float) {
        bottomText.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
    }

    fun setBottomPanelButtonText(text: CharSequence?) {
        bottomBtn.text = text
        bottomBtn.visibility = if (text.isNullOrBlank()) View.INVISIBLE else View.VISIBLE
    }

    fun setBottomPanelButtonTextSize(size: Float) {
        bottomBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
    }

    fun setupBottomPanelButtonBackground(
        backgroundColor: String? = null,
        borderColor: String? = null,
        borderWidth: Int = 0,
        cornerRadius: Int = 32,
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

        bottomBtn.background = StateListDrawable().apply {
            addState(intArrayOf(android.R.attr.state_pressed), pressedDrawable)
            addState(intArrayOf(), defaultDrawable)
        }

        bottomBtn.backgroundTintList = null
    }

    fun setBottomPanelButtonListener(l: OnClickListener) {
        bottomBtn.setOnClickListener(l)
    }
}