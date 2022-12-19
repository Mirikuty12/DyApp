package com.dynamicyield.templates.ui.refinance

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.dynamicyield.templates.R
import com.dynamicyield.templates.ui.DyWidget
import com.dynamicyield.templates.ui.DyWidgetName
import com.dynamicyield.templates.ui.base.recyclerview.DelegateAdapter
import com.dynamicyield.templates.ui.base.recyclerview.bind
import com.dynamicyield.templates.ui.base.recyclerview.create
import com.dynamicyield.templates.ui.base.recyclerview.itemDelegate
import com.dynamicyield.templates.ui.base.util.dpToPx

class RefinanceSliderView : ConstraintLayout, DyWidget {
    private var refinanceSliderListener: RefinanceSliderListener? = null

    private val refinanceAdapter = DelegateAdapter(createRefinanceDelegate())

    private lateinit var recyclerView: RecyclerView
    private lateinit var pagerSnapHelper: PagerSnapHelper

    override val dyName = DyWidgetName.RefinanceSlider

    constructor(context: Context) : super(context) {
        dyInit(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        dyInit(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        dyInit(context, attrs, defStyleAttr)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        dyInit(context, attrs, defStyleAttr, defStyleRes)
    }

    private fun dyInit(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
    ) {
        inflate(context, R.layout.refinance_slider_layout, this)

        // init internal variables
        recyclerView = findViewById(R.id.recyclerView)
        pagerSnapHelper = PagerSnapHelper()

        // setup Recycler View
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = refinanceAdapter
        pagerSnapHelper.attachToRecyclerView(recyclerView)
    }

    fun setRefinanceDataList(refinances: List<RefinanceData>) {
        refinanceAdapter.submitList(refinances)
    }

    fun setRefinanceSliderListener(listener: RefinanceSliderListener?) {
        refinanceSliderListener = listener
    }

    private fun createRefinanceDelegate() = itemDelegate<RefinanceData> { context ->
        RefinanceView(context)
    }.create { parent ->
        itemView.apply {
            layoutParams = RecyclerView.LayoutParams(
                parent.measuredWidth - (2 * 16 + 2 * 36).toFloat().dpToPx(),
                RecyclerView.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(8f.dpToPx(), 0, 8f.dpToPx(), 0)
            }
        }
    }.bind { position, refinanceData ->
        (itemView as? RefinanceView)?.apply {
            setCornerRadius(refinanceData.cornerRadius.toFloat())
            setBackgroundColorStr(refinanceData.backgroundColor)
            setImage(
                url = refinanceData.image,
                scaleType = refinanceData.imageScaleType,
            )
            setTitle(
                text = refinanceData.title,
                textColor = refinanceData.titleTextColor,
                textSize = refinanceData.titleTextSize.toFloat(),
            )
            setSubtitle(
                text = refinanceData.subtitle,
                textColor = refinanceData.subtitleTextColor,
                textSize = refinanceData.subtitleTextSize.toFloat(),
            )
            setCtaButton1(
                buttonText = refinanceData.ctaButton1Text,
                buttonTextSizeSp = refinanceData.ctaButton1TextSize.toFloat(),
                buttonTextColorString = refinanceData.ctaButton1TextColor,
                pressedButtonTextColorString = refinanceData.ctaButton1PressedTextColor,
                buttonBackgroundColorString = refinanceData.ctaButton1BackgroundColor,
                pressedButtonBackgroundColorString = refinanceData.ctaButton1PressedBackgroundColor,
                buttonStrokeColorString = refinanceData.ctaButton1StrokeColor,
                buttonStrokeWidth = refinanceData.ctaButton1StrokeWidth,
            )
            setCtaButton2(
                buttonText = refinanceData.ctaButton2Text,
                buttonTextSizeSp = refinanceData.ctaButton2TextSize.toFloat(),
                buttonTextColorString = refinanceData.ctaButton2TextColor,
                pressedButtonTextColorString = refinanceData.ctaButton2PressedTextColor,
                buttonBackgroundColorString = refinanceData.ctaButton2BackgroundColor,
                pressedButtonBackgroundColorString = refinanceData.ctaButton2PressedBackgroundColor,
                buttonStrokeColorString = refinanceData.ctaButton2StrokeColor,
                buttonStrokeWidth = refinanceData.ctaButton2StrokeWidth,
            )
            setRefinanceListener(object : RefinanceView.RefinanceListener {
                override fun onCtaButton1Click(v: View) {
                    refinanceSliderListener?.onCtaButton1Click(position, refinanceData)
                }

                override fun onCtaButton2Click(v: View) {
                    refinanceSliderListener?.onCtaButton2Click(position, refinanceData)
                }
            })
        }
    }

    interface RefinanceSliderListener {
        fun onCtaButton1Click(position: Int, refinanceData: RefinanceData)
        fun onCtaButton2Click(position: Int, refinanceData: RefinanceData)
    }
}